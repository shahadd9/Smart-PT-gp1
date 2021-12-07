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

eqEx=pd.DataFrame()


planlist = []
def exercises (bench,barbell,stabilityBall,dumbbell,dipMachine,cableMachine):
        global eqEx,upper,upper1,upper2,upper3,upper4,lower,lower1,lower2

        NoEq=df.loc[df['isneedequipment'] == 0]
        NoEq['equipment'] = 'No Need to Equipment'
        eqEx=pd.concat([NoEq, eqEx], axis=0)
   
        if(bench==True):
            benchEx=  df.loc[df['bench'] == 1]
            benchEx['equipment']='bench'
            eqEx=pd.concat([eqEx, benchEx], axis=0)


        if(barbell==True):
            barbellEx=  df.loc[df['barbell'] == 1]
            barbellEx['equipment']='barbell'
            eqEx=pd.concat([eqEx, barbellEx], axis=0)

        if(stabilityBall==True):
            stabilityballEx=  df.loc[df['stability ball'] == 1]
            stabilityballEx['equipment']='stability ball'

            eqEx=pd.concat([eqEx, stabilityballEx], axis=0)


        if(dumbbell==True):
            DumbbellEx=  df.loc[df['dumbbell'] == 1]
            DumbbellEx['equipment']='dumbbell'

            eqEx=pd.concat([eqEx, DumbbellEx], axis=0)



        if(dipMachine==True):
            dipmachineEx=  df.loc[df['dip machine'] == 1]
            dipmachineEx['equipment']='dip machine'

            eqEx=pd.concat([eqEx, dipmachineEx], axis=0)


        if(cableMachine==True):
            cablemachineEx=  df.loc[df['cable machine'] == 1]
            cablemachineEx['equipment']='cable machine'

            eqEx=pd.concat([eqEx, cablemachineEx], axis=0)
        eqEx.drop(['isneedequipment','bench','barbell','stability ball','dumbbell','dip machine','cable machine'], axis=1, inplace=True)
        eqEx.drop(['lower pectoralis major','upper pectoralis major','anterior deltoid','triceps brachii' ,'tensor fasciae latae','sartorius',' pectineus,adductor longus','adductor brevis','rectus abdominis','iliopsoas','rectus femoris','quadriceps','serratus anterior','latissimus dorsi','pectoralis minor','rhomboids','levator scapulae','teres major','biceps brachii','brachialis','brachioradialis','internal and external obliques','rectus abdominis','infraspinatus','teres minor','lateral deltoid','middle and lower trapezius','hamstrings','adductor magnus','gracilis','obliques','gluteus maximus','supraspinatus','posterior deltoid','pectoralis major','quadratus lumborum','psoas major','iliocastalis iumborum','iliocastalis Thoracis','Soleus','gastrocnemius','popliteus','gluteus medius','gluteus minimus','flexor carpi radialis','anconeus'], axis=1, inplace=True)




        upper=eqEx.loc[eqEx['generalmuscle'].isin(['chest','arm','back','shoulders','back,arm'])]
        upper1=eqEx.loc[eqEx['generalmuscle'].isin(['chest'])]
        upper2=eqEx.loc[eqEx['generalmuscle'].isin(['arm'])]
        upper3=eqEx.loc[eqEx['generalmuscle'].isin(['shoulders'])]
        upper4=eqEx.loc[eqEx['generalmuscle'].isin(['back','back,arm'])]
        lower=eqEx.loc[eqEx['generalmuscle'].isin(['core','legs and glutes'])]
        lower1=eqEx.loc[eqEx['generalmuscle'].isin(['core'])]
        lower2=eqEx.loc[eqEx['generalmuscle'].isin(['legs and glutes'])]




def fullbody(level): 
    ex1=upper1.sample()
    ex2=upper2.sample()
    ex3=upper3.sample()
    ex4=upper4.sample()
    ex5=upper.sample()
    ex6=lower1.sample()
    ex7=lower2.sample()
    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    
    if(level=="intermediate" or level=="professional"):
        ex8=upper.sample()
        ex9=upper.sample()
        ex10=lower.sample()
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    if(level=="professional"):
        ex11=upper.sample()
        ex12=lower.sample()
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1]) 
    return rs.to_numpy() 
    
    

def upperbody(level): 
    ex1=upper1.sample()
    ex2=upper2.sample()
    ex3=upper3.sample()
    ex4=upper4.sample()
    ex5=upper1.sample()
    ex6=upper2.sample()
    ex7=upper3.sample()
    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    
    if(level=="intermediate" or level=="professional"):
        ex8=upper4.sample()
        ex9=upper.sample()
        ex10=upper.sample()
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    if(level=="professional"):
        ex11=upper.sample()
        ex12=upper.sample()
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1]) 
    return rs.to_numpy()

def lowerbody(level): 
    ex1=lower1.sample()
    ex2=lower2.sample()
    ex3=lower1.sample()
    ex4=lower2.sample()
    ex5=lower1.sample()
    ex6=lower2.sample()
    ex7=lower.sample()
    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    
    if(level=="intermediate" or level=="professional"):
        ex8=lower.sample()
        ex9=lower.sample()
        ex10=lower.sample()
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    if(level=="professional"):
        ex11=lower.sample()
        ex12=lower.sample()
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1]) 
    return rs.to_numpy()