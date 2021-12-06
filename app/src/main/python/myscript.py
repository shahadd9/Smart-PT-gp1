
import io
import pandas as pd
from os.path import dirname, join
from io import StringIO
import numpy as np



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



planlist = []
def exercises (bench,barbell,stabilityBall,dumbbell,dipMachine,cableMachine):
    
        if(bench==True):
            benchEx=  dfDetails.loc[dfDetails['bench'] == 1]
            arrrbench=benchEx.to_numpy()
            planlist.append(arrrbench)
        if(barbell==True):
            barbellEx=  dfDetails.loc[dfDetails['barbell'] == 1]
            arrbarbell=barbellEx.to_numpy()
            planlist.append(arrbarbell)

        if(stabilityBall==True):
            stabilityballEx=  dfDetails.loc[dfDetails['stability ball'] == 1]
            arrSB=stabilityballEx.to_numpy()
            planlist.append(arrSB)

        if(dumbbell==True):
            DumbbellEx=  dfDetails.loc[dfDetails['Dumbbell'] == 1]
            arrDumbbell=DumbbellEx.to_numpy()
            planlist.append(arrDumbbell)

        if(dipMachine==True):
            dipmachineEx=  dfDetails.loc[dfDetails['dip machine'] == 1]
            arrDM=dipmachineEx.to_numpy()
            planlist.append(arrDM)

        if(cableMachine==True):
            cablemachineEx=  dfDetails.loc[dfDetails['cable machine'] == 1]
            arrCM=cablemachineEx.to_numpy()
            planlist.append(arrCM)
          
        NoEq=dfDetails.loc[dfDetails['isNeedEquipment'] == 0]
        arrNoEq=NoEq.to_numpy()
        planlist.append(arrNoEq)

        plan = np.array(planlist)
        return plan