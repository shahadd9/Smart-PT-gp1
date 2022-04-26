import io
from pickle import TRUE
import pandas as pd
from os.path import dirname, join
from io import StringIO
import numpy as np
import sklearn
import csv
import os
from os.path import join



# filename = join(dirname(__file__), "progress.csv")
# with open(filename, 'r', encoding='utf8', errors="ignore") as fin:
#     data1=fin.read()
# data = io.StringIO(data1)
# df = pd.read_csv(data, sep=",") # here i read our dataset as dataframe, you can use it as normal

def saveProgress(id,exName,today,sets):

    filename = join(dirname(__file__), "progress.csv")
    with open(filename, 'r', encoding='utf8', errors="ignore") as fin:
         data1=fin.read()
    data = io.StringIO(data1)
    df = pd.read_csv(data, sep=",") # here i read our dataset as dataframe, you can use it as normal

    df.loc[len(df.index)] = [id,exName, today,sets,10,'enter']

    df.to_csv(join(os.environ["HOME"], "progress.csv"))

    filename = join(os.environ["HOME"], "progress.csv")
    with open(filename, 'r', encoding='utf8', errors="ignore") as fin:
        data1=fin.read()
    data = io.StringIO(data1)
    df1= pd.read_csv(data, sep=",") # here i read our dataset as dataframe, you can use it as normal
    return df1.loc[len(df1.index)-2]