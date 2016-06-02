#!/usr/bin/env python

from BeautifulSoup import BeautifulSoup
import json
import urllib
import urllib2
import re
import time
import os.path

def getgoogleurl(search,siteurl=False):
    search = search.encode("utf-8")
    if siteurl==False:
        return 'http://www.google.com/search?q='+urllib.quote_plus(search)
    else:
        return 'http://www.google.com/search?q=site:'+urllib.quote_plus(siteurl)+'%20'+urllib.quote_plus(search)

def getgooglelinks(search,siteurl=False):
    #google returns 403 without user agent
    headers = {'User-agent':'Safari/537.36'}
    req = urllib2.Request(getgoogleurl(search,siteurl),None,headers)
    site = urllib2.urlopen(req)
    data = site.read()
    site.close()
    m = re.search("([0-9.]+) sonu", data)
    count = -1
    if m:
        count = m.groups()[0].replace(".", "")
    return int(count)


names = {}

if os.path.exists("names.txt"):
    with open("names.txt") as f:
        for line in f.readlines():
            tokens = line.split(" ")
            names[tokens[0].decode("utf-8")] = (tokens[0].decode("utf-8"), tokens[1] == "1", int(tokens[2]), tokens[3].decode("utf-8"))

f = open("names.txt", 'a+')
print_counter = 0


def handle_span(dct, spans, is_male, fl):
    global print_counter

    p = re.compile('&uid=(\d+)&')
    for span in spans:
        n = span.parent.text.split()[0]
        m = p.search(span.parent["href"])
        nameId = m.group(1)
        if n not in dct:
            ncnt = -1
            for _i in range(5):
                try:
                    ncnt = getgooglelinks(n)
                    break
                except Exception as err:
                    print err, n
                    time.sleep(5)
            if ncnt == -1:
                raise Exception("getgooglelinks not working")
            dct[n] = (n, is_male, ncnt, nameId)
            fl.write("%s %d %d %s\n" % (n.encode("utf-8"), is_male, ncnt, nameId.encode("utf-8")))
            if print_counter % 40 == 0:
                print n, is_male, ncnt, nameId
            print_counter += 1
        else:
            print "skipping", n

searching = "aeiou"
beginning = 0
pageno = 1
page = 1
searchforindex = 0

guid = urllib.urlencode({'guid': "TDK.GTS.574eccc8396288.52796697"})

if os.path.exists('names_input.txt'):
    with open('names_input.txt') as ini:
        beginning, pageno = map(int, ini.readline().split())

try:
    for searchforindex in range(beginning, len(searching)):
        searchfor = searching[searchforindex]
        pagebegin = 1 if searchforindex > beginning else pageno
        tokenq = urllib.urlencode({'name': searchfor})
        for page in range(pagebegin, 122):
            print "fetching", page, "of", searchfor
            pageq = urllib.urlencode({'page': page})
            url = 'http://tdk.gov.tr/index.php?option=com_kisiadlari&arama=adlar&like=0&cinsi=0&turu=0&%s&%s&%s' % (guid, pageq, tokenq)
            response = None
            for _i in range(5):
                try:
                    response = urllib.urlopen(url)
                    break
                except Exception as err:
                    print err
                    time.sleep(5)
            if not response:
                raise Exception("urllib.urlopen not working for " + url)

            soup = BeautifulSoup(response)
            female_spans = soup.body.findAll('span', attrs={'id' : 'cinsiyet1'})
            male_spans = soup.body.findAll('span', attrs={'id' : 'cinsiyet2'})
            handle_span(names, female_spans, False, f)
            handle_span(names, male_spans, True, f)
except Exception as e:
    print e.__doc__
    print e.message
    
ini = open("names_input.txt", 'w+')
ini.write("%d %d\n" % (searchforindex, page))
ini.close()
f.close()
