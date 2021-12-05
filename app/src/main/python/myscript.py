
import io
import pandas as pd
from os.path import dirname, join
from io import StringIO



filename = join(dirname(__file__), "dataset.csv")
with open(filename, 'r', encoding='utf8', errors="ignore") as fin:
    data1=fin.read().lower()

data = io.StringIO(data1)

df = pd.read_csv(data, sep=",") # here i read our dataset as dataframe, you can use it as normal

filename1 = join(dirname(__file__), "details.csv")
with open(filename1, 'r', encoding='utf8', errors="ignore") as fin:
    dataDetails=fin.read().lower()

data = io.StringIO(dataDetails)

dfDetails = pd.read_csv(data, sep=",")

print(dfDetails['id'])



def exercises(bench,barbell,stabilityBall,dumbbell,dipMachine,cableMachine):
    if bench:#test
        b= False#test

    return b#test


