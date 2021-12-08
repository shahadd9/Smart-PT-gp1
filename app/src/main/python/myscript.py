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

# print(dfDetails['id'])

eqEx=pd.DataFrame()


planlist = []
def exercises (bench,barbell,stabilityBall,dumbbell,dipMachine,cableMachine):
        global eqEx,upper,upper1,upper2,upper3,upper4,lower,lower1,lower2
        df.drop(df.iloc[:, 7:67].columns, axis = 1)

        NoEq=df.loc[df['isneedequipment'] == 0]
        eqEx=pd.concat([NoEq, eqEx], axis=0)

        if(bench==True):
            benchEx=  df.loc[df['bench'] == 1]
            eqEx=pd.concat([eqEx, benchEx], axis=0)


        if(barbell==True):
            barbellEx=  df.loc[df['barbell'] == 1]
            eqEx=pd.concat([eqEx, barbellEx], axis=0)

        if(stabilityBall==True):
            stabilityballEx=  df.loc[df['stability ball'] == 1]

            eqEx=pd.concat([eqEx, stabilityballEx], axis=0)


        if(dumbbell==True):
            DumbbellEx=  df.loc[df['dumbbell'] == 1]

            eqEx=pd.concat([eqEx, DumbbellEx], axis=0)



        if(dipMachine==True):
            dipmachineEx=  df.loc[df['dip machine'] == 1]

            eqEx=pd.concat([eqEx, dipmachineEx], axis=0)


        if(cableMachine==True):
            cablemachineEx=  df.loc[df['cable machine'] == 1]

            eqEx=pd.concat([eqEx, cablemachineEx], axis=0)




        upper=eqEx.loc[eqEx['generalmuscle'].isin(['chest','arm','back','shoulders','back,arm'])]
        upper1=eqEx.loc[eqEx['generalmuscle'].isin(['chest'])]
        upper2=eqEx.loc[eqEx['generalmuscle'].isin(['arm'])]
        upper3=eqEx.loc[eqEx['generalmuscle'].isin(['shoulders'])]
        upper4=eqEx.loc[eqEx['generalmuscle'].isin(['back','back,arm'])]
        lower=eqEx.loc[eqEx['generalmuscle'].isin(['core','legs-and-glutes'])]
        lower1=eqEx.loc[eqEx['generalmuscle'].isin(['core'])]
        lower2=eqEx.loc[eqEx['generalmuscle'].isin(['legs-and-glutes'])]




# def fullbody(level):
#     ex1=upper1.sample()
#     ex2=upper2.sample()
#     ex3=upper3.sample()
#     ex4=upper4.sample()
#     ex5=upper.sample()
#     ex6=lower1.sample()
#     ex7=lower2.sample()
#     rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])
#
#     if(level=="Intermediate" or level=="Professional"):
#         ex8=upper.sample()
#         ex9=upper.sample()
#         ex10=lower.sample()
#         rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
#     if(level=="Professional"):
#         ex11=upper.sample()
#         ex12=lower.sample()
#         rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
#     rs=rs.drop(rs.columns[[ 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
#     return rs.to_numpy()

def fullbody(level):
    global fullbodygeneralmuscle,fullbodyforce,fullbodyname
    ex1=upper1.sample()
    ex2=upper2.sample()
    ex3=upper3.sample()
    ex4=upper4.sample()
    ex5=upper.sample()
    ex6=lower1.sample()
    ex7=lower2.sample()
    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])

    if(level=="Intermediate" or level=="Professional"):
        ex8=upper.sample()
        ex9=upper.sample()
        ex10=lower.sample()
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    if(level=="Professional"):
        ex11=upper.sample()
        ex12=lower.sample()
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    fullbodyname=rs.copy()
    fullbodyforce=rs.copy()
    fullbodygeneralmuscle=rs.copy()

    fullbodyname=fullbodyname.drop(rs.columns[[ 0,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    fullbodyforce=fullbodyforce.drop(rs.columns[[ 0,1,2,3,4,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    fullbodygeneralmuscle=fullbodygeneralmuscle.drop(rs.columns[[ 0,1,2,3,4,5,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)

 #   return rs.to_numpy()

def upperbody(level):
    global upperbodyname,upperbodyforce,upperbodygeneralmuscle
    ex1=upper1.sample()
    ex2=upper2.sample()
    ex3=upper3.sample()
    ex4=upper4.sample()
    ex5=upper1.sample()
    ex6=upper2.sample()
    ex7=upper3.sample()
    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])

    if(level=="Intermediate" or level=="Professional"):
        ex8=upper4.sample()
        ex9=upper.sample()
        ex10=upper.sample()
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    if(level=="Professional"):
        ex11=upper.sample()
        ex12=upper.sample()
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    upperbodyname=rs.copy()
    upperbodyforce=rs.copy()
    upperbodygeneralmuscle=rs.copy()

    upperbodyname=upperbodyname.drop(rs.columns[[ 0,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    upperbodyforce=upperbodyforce.drop(rs.columns[[ 0,1,2,3,4,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    upperbodygeneralmuscle=upperbodygeneralmuscle.drop(rs.columns[[ 0,1,2,3,4,5,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)

 #   return rs.to_numpy()

def lowerbody(level):
    global lowerbodygeneralmuscle,lowerbodyforce,lowerbodyname
    ex1=lower1.sample()
    ex2=lower2.sample()
    ex3=lower1.sample()
    ex4=lower2.sample()
    ex5=lower1.sample()
    ex6=lower2.sample()
    ex7=lower.sample()
    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])

    if(level=="Intermediate" or level=="Professional"):
        ex8=lower.sample()
        ex9=lower.sample()
        ex10=lower.sample()
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    if(level=="Professional"):
        ex11=lower.sample()
        ex12=lower.sample()
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    lowerbodyname=rs.copy()
    lowerbodyforce=rs.copy()
    lowerbodygeneralmuscle=rs.copy()

    lowerbodyname=lowerbodyname.drop(rs.columns[[ 0,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    lowerbodyforce=lowerbodyforce.drop(rs.columns[[ 0,1,2,3,4,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    lowerbodygeneralmuscle=lowerbodygeneralmuscle.drop(rs.columns[[ 0,1,2,3,4,5,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    
    #return rs.to_numpy()

def fiveDay(level,d):
    global fiveDayname,fiveDayforce,fiveDaygeneralmuscle
    if(d==1):
        musels=eqEx.loc[eqEx['generalmuscle'].isin(['chest'])]
    if(d==2):
        musels=eqEx.loc[eqEx['generalmuscle'].isin(['back','legs and glutes'])]

    if(d==3):
        musels=eqEx.loc[eqEx['generalmuscle'].isin(['shoulders'])]
    if(d==4):
        musels=eqEx.loc[eqEx['generalmuscle'].isin(['core'])]
    if(d==5):
        musels=eqEx.loc[eqEx['generalmuscle'].isin(['arm','back,arm'])]

    ex1=musels.sample()
    ex2=musels.sample()
    ex3=musels.sample()
    ex4=musels.sample()
    ex5=musels.sample()
    ex6=musels.sample()
    ex7=musels.sample()
    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])

    if(level=="Intermediate" or level=="Professional"):
        ex8=musels.sample()
        ex9=musels.sample()
        ex10=musels.sample()
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    if(level=="Professional"):
        ex11=musels.sample()
        ex12=musels.sample()
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1]) 
    
    fiveDayname=rs.copy()
    fiveDayforce=rs.copy()
    fiveDaygeneralmuscle=rs.copy()

    fiveDayname=fiveDayname.drop(rs.columns[[ 0,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    fiveDayforce=fiveDayforce.drop(rs.columns[[ 0,1,2,3,4,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    fiveDaygeneralmuscle=fiveDaygeneralmuscle.drop(rs.columns[[ 0,1,2,3,4,5,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
   # return rs.to_numpy()

def getfullbodyName():
    str1 = " " 
    
    # traverse in the string  
    arr=fullbodyname.to_numpy()
    for ele in arr: 
        str1 += ele
        
    return str1
    
def getfullbodyForce():
    str1 = "" 
    
    arr=fullbodyforce.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1 
       

def getfullbodygeneralmuscle():
    str1 = "" 
    
    arr=fullbodygeneralmuscle.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1 
   




def getupperName():
    str1 = "" 
    
    arr=upperbodyname.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1 
   

def getupperForce():
    str1 = "" 
    
    arr=upperbodyforce.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1    

def getuppergeneralmuscle():
    str1 = "" 
    
    arr=upperbodygeneralmuscle.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1    



def getlowerName():
    str1 = "" 
    
    arr=lowerbodyname.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1    
   

def getlowerForce():
    str1 = "" 
    
    arr=lowerbodyforce.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1

def getlowergeneralmuscle():
    str1 = "" 
    
    arr=lowerbodygeneralmuscle.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1





def getfivedayName():
    str1 = "" 
    
    arr=fiveDayname.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1
   
def getfivedayForce():
    str1 = "" 
    
    arr=fiveDayforce.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1

def getfivedaymuscle():
    str1 = "" 
    
    arr=fiveDaygeneralmuscle.to_numpy()
    for ele in arr: 
        str1 += ele+"."

    return str1
