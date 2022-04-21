import io
from pickle import TRUE
import pandas as pd
from os.path import dirname, join
from io import StringIO
import numpy as np
import sklearn




filename = join(dirname(__file__), "progress.csv")
with open(filename, 'r', encoding='utf8', errors="ignore") as fin:
    data1=fin.read()
data = io.StringIO(data1)
df = pd.read_csv(data, sep=",") # here i read our dataset as dataframe, you can use it as normal

def saveProgress(exName):
