import io
from pickle import TRUE
import pandas as pd
from os.path import dirname, join
from io import StringIO
import numpy as np


filename3 = join(dirname(__file__), "instructions.csv")
with open(filename3, 'r', encoding='utf8', errors="ignore") as fin:
    data3=fin.read().lower()
data4 = io.StringIO(data3)
inst = pd.read_csv(data4, sep=",")

print(inst['pos1'])