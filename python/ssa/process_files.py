#!/usr/bin/env python

import glob, os
import operator

names = {}

for file in glob.glob("files/*.txt"):
    print(file)
    with open(file) as f:
        for line in f:
            values = line.split(',')
            name = values[0]
            gender = "1" if values[1] == 'M' else "0"
            freq = int(values[2])
            key = name + "_" + gender
            
            if key in names:
                names[key] = (name, gender, max(names[key][2], freq))
            else:
                names[key] = (name, gender, freq)

sorted_names = sorted(names.items(), key=operator.itemgetter(0))
with open('en.txt', 'w') as f:
    for n in sorted_names:
        f.write(n[1][0] + " " + n[1][1] + " " + str(n[1][2]) + "\n")
