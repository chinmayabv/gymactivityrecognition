"""
@author: preethamganesh
@email: preetham.ganesh@mavs.uta.edu
"""

import pandas as pd

def fileConversion(filepath):
    x, y, c, f = [], [], [], []
    with open(filepath, 'r') as file:
        txt = file.read().replace('\n', '')
    txt = txt[1:-1]
    txt = txt.replace('([], [], [], [])', '')
    l1 = list(txt.split(']['))
    for j in l1:
        j = j[1:-1]
        l = list(j.split('], ['))
        m = []
        for k in l:
            k = k[1:-1]
            n = list(k.split(', '))
            if len(n) == 1:
                n = [0, 0, 0]
            m.append(n)
        print(m)
        print()
        for o in m:
            x.append(int(o[0]))
            y.append(int(o[1]))
            c.append(float(o[2]))
    d = {}
    col_x, col_y, col_c = [], [], [] 
    for i in range(18):
        col_x.append('x_'+str(i))
        col_y.append('y_'+str(i))
        col_c.append('c_'+str(i))
    for j, k, l in zip(col_x, col_y, col_c):
        d[j], d[k], d[l] = [], [], []
    for j in range(18):
        for k in range(0, len(x), 18):
            d['x_'+str(j)].append(x[k+j])
            d['y_'+str(j)].append(y[k+j])
            d['c_'+str(j)].append(c[k+j])
    for j in range(len(l1)):
        f.append(j)
    d['frame'] = f
    col = col_x + col_y + col_c
    col.append('frame')
    df = pd.DataFrame(d, columns=col)
    return df