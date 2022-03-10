import io
from pickle import TRUE
import pandas as pd
from os.path import dirname, join
from io import StringIO
import numpy as np
import sklearn
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel
from sklearn.metrics.pairwise import cosine_similarity



#execl1
filename = join(dirname(__file__), "dataset.csv")
with open(filename, 'r', encoding='utf8', errors="ignore") as fin:
    data1=fin.read()
data = io.StringIO(data1)
df = pd.read_csv(data, sep=",") # here i read our dataset as dataframe, you can use it as normal
#excel2
filename1 = join(dirname(__file__), "matrix.csv")
with open(filename1, 'r', encoding='utf8', errors="ignore") as fin:
    data2=fin.read()
data3 = io.StringIO(data2)
df1 = pd.read_csv(data3, sep=",")

#excel3
filename3 = join(dirname(__file__), "instructions.csv")
with open(filename3, 'r', encoding='utf8', errors="ignore") as fin:
    data4=fin.read()
data5 = io.StringIO(data4)
inst1 = pd.read_csv(data5, sep=",")

df1 = df1.iloc[: , 2:]
# df1=pd.read_csv('matrix.csv')
# print(df1)



eqEx=pd.DataFrame()

planlist = []
def exercises (Bench,Barbell,stabilityBall,dumbbell,dipMachine,cableMachine):
        global eqEx,upper,upper1,upper2,upper3,upper4,lower,lower1,lower2,d1,d2,d3,d4,d5
        df.drop(df.iloc[:, 7:67].columns, axis = 1)

        NoEq=df.loc[df['isNeedEquipment'] == 0]
        eqEx=pd.concat([NoEq, eqEx], axis=0)

        if(Bench==True):
            BenchEx=  df.loc[df['Bench'] == 1]
            eqEx=pd.concat([eqEx, BenchEx], axis=0)


        if(Barbell==True):
            BarbellEx=  df.loc[df['Barbell'] == 1]
            eqEx=pd.concat([eqEx, BarbellEx], axis=0)

        if(stabilityBall==True):
            stabilityballEx=  df.loc[df['Stability Ball'] == 1]

            eqEx=pd.concat([eqEx, stabilityballEx], axis=0)


        if(dumbbell==True):
            DumbbellEx=  df.loc[df['Dumbbell'] == 1]

            eqEx=pd.concat([eqEx, DumbbellEx], axis=0)



        if(dipMachine==True):
            dipmachineEx=  df.loc[df['Dip Machine'] == 1]

            eqEx=pd.concat([eqEx, dipmachineEx], axis=0)


        if(cableMachine==True):
            cablemachineEx=  df.loc[df['Cable Machine'] == 1]

            eqEx=pd.concat([eqEx, cablemachineEx], axis=0)




        upper=eqEx.loc[eqEx['generalMuscle'].isin(['chest','Arm','Back','Shoulders','Back,Arm'])]
        upper1 = d1=eqEx.loc[eqEx['generalMuscle'].isin(['chest'])]
        upper2=eqEx.loc[eqEx['generalMuscle'].isin(['Arm'])]
        upper3=d3=eqEx.loc[eqEx['generalMuscle'].isin(['Shoulders'])]
        upper4=eqEx.loc[eqEx['generalMuscle'].isin(['Back','Back,Arm'])]
        lower=eqEx.loc[eqEx['generalMuscle'].isin(['core','Legs and Glutes'])]
        lower1=d4=eqEx.loc[eqEx['generalMuscle'].isin(['core'])]
        lower2=eqEx.loc[eqEx['generalMuscle'].isin(['Legs and Glutes'])]
        d2=eqEx.loc[eqEx['generalMuscle'].isin(['Back','Legs and Glutes'])]
        d5=eqEx.loc[eqEx['generalMuscle'].isin(['Arm','Back,Arm'])]




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
    global fullbodygeneralMuscle,fullbodyForce,fullbodyname
    ex1=upper1.sample()
    ex2=upper2.sample()

    if(ex1.iat[0,1] == ex2.iat[0,1] ):
        fullbody(level)
    
    ex3=upper3.sample()
    if(ex3.iat[0,1] == ex2.iat[0,1] or ex3.iat[0,1] == ex1.iat[0,1]):
        fullbody(level)
        
    ex4=upper4.sample()
    if(ex4.iat[0,1] == ex3.iat[0,1] or ex4.iat[0,1] == ex2.iat[0,1] or ex4.iat[0,1] == ex1.iat[0,1]):
        fullbody(level)
        
    ex5=upper.sample()
    if(ex5.iat[0,1] == ex4.iat[0,1] or ex5.iat[0,1] == ex3.iat[0,1] or ex5.iat[0,1] == ex2.iat[0,1] or ex5.iat[0,1] == ex1.iat[0,1]):
        fullbody(level)
        
    ex6=lower1.sample()
    if(ex6.iat[0,1] == ex5.iat[0,1] or ex6.iat[0,1] == ex4.iat[0,1] or ex6.iat[0,1] == ex3.iat[0,1] or ex6.iat[0,1] == ex2.iat[0,1] or ex6.iat[0,1] == ex1.iat[0,1]):
        fullbody(level)
        
    ex7=lower2.sample()
    if(ex7.iat[0,1] == ex6.iat[0,1] or ex7.iat[0,1] == ex5.iat[0,1] or ex7.iat[0,1] == ex4.iat[0,1] or ex7.iat[0,1] == ex3.iat[0,1] or ex7.iat[0,1] == ex2.iat[0,1] or ex7.iat[0,1] == ex1.iat[0,1]):
        fullbody(level)
        
    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])

    if(level=="Intermediate" or level=="Professional"):
        ex8=upper.sample()
        if(ex8.iat[0,1] == ex7.iat[0,1] or ex8.iat[0,1] == ex6.iat[0,1] or ex8.iat[0,1] == ex5.iat[0,1] or ex8.iat[0,1] == ex4.iat[0,1] or ex8.iat[0,1] == ex3.iat[0,1] or ex8.iat[0,1] == ex2.iat[0,1] or ex8.iat[0,1] == ex1.iat[0,1]):
            fullbody(level) 
            
        ex9=upper.sample()
        if(ex9.iat[0,1] == ex8.iat[0,1] or ex9.iat[0,1] == ex7.iat[0,1] or ex9.iat[0,1] == ex6.iat[0,1] or ex9.iat[0,1] == ex5.iat[0,1] or ex9.iat[0,1] == ex4.iat[0,1] or ex9.iat[0,1] == ex3.iat[0,1] or ex9.iat[0,1] == ex2.iat[0,1] or ex9.iat[0,1] == ex1.iat[0,1]):
            fullbody(level) 
            
        ex10=lower.sample()
        if(ex10.iat[0,1] == ex9.iat[0,1] or ex10.iat[0,1] == ex8.iat[0,1] or ex10.iat[0,1] == ex7.iat[0,1] or ex10.iat[0,1] == ex6.iat[0,1] or ex10.iat[0,1] == ex5.iat[0,1] or ex10.iat[0,1] == ex4.iat[0,1] or ex10.iat[0,1] == ex3.iat[0,1] or ex10.iat[0,1] == ex2.iat[0,1] or ex10.iat[0,1] == ex1.iat[0,1]):
            fullbody(level) 
            
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
        
    if(level=="Professional"):
        ex11=upper.sample()
        if(ex11.iat[0,1] == ex10.iat[0,1] or ex11.iat[0,1] == ex9.iat[0,1] or ex11.iat[0,1] == ex8.iat[0,1] or ex11.iat[0,1] == ex7.iat[0,1] or ex11.iat[0,1] == ex6.iat[0,1] or ex11.iat[0,1] == ex5.iat[0,1] or ex11.iat[0,1] == ex4.iat[0,1] or ex11.iat[0,1] == ex3.iat[0,1] or ex11.iat[0,1] == ex2.iat[0,1] or ex11.iat[0,1] == ex1.iat[0,1]):
            fullbody(level) 
            
        ex12=lower.sample()
        if(ex12.iat[0,1] == ex11.iat[0,1] or ex12.iat[0,1] == ex10.iat[0,1] or ex12.iat[0,1] == ex9.iat[0,1] or ex12.iat[0,1] == ex8.iat[0,1] or ex12.iat[0,1] == ex7.iat[0,1] or ex12.iat[0,1] == ex6.iat[0,1] or ex12.iat[0,1] == ex5.iat[0,1] or ex12.iat[0,1] == ex4.iat[0,1] or ex12.iat[0,1] == ex3.iat[0,1] or ex12.iat[0,1] == ex2.iat[0,1] or ex12.iat[0,1] == ex1.iat[0,1]):
            fullbody(level) 
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    fullbodyname=rs.copy()
    fullbodyForce=rs.copy()
    fullbodygeneralMuscle=rs.copy()

    fullbodyname=fullbodyname.drop(rs.columns[[ 0,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    fullbodyForce=fullbodyForce.drop(rs.columns[[ 0,1,2,3,4,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    fullbodygeneralMuscle=fullbodygeneralMuscle.drop(rs.columns[[ 0,1,2,3,4,5,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)

 #   return rs.to_numpy()

def upperbody(level):
    global upperbodyname,upperbodyForce,upperbodygeneralMuscle
    ex1=upper1.sample()
    ex2=upper2.sample()
    
    if(ex1.iat[0,1] == ex2.iat[0,1] ):
      upperbody(level)    
    
    ex3=upper3.sample()
    if(ex3.iat[0,1] == ex2.iat[0,1] or ex3.iat[0,1] == ex1.iat[0,1]):
      upperbody(level)    
    
    ex4=upper4.sample()
    if(ex4.iat[0,1] == ex3.iat[0,1] or ex4.iat[0,1] == ex2.iat[0,1] or ex4.iat[0,1] == ex1.iat[0,1]):
      upperbody(level)
    
    ex5=upper1.sample()
    if(ex5.iat[0,1] == ex4.iat[0,1] or ex5.iat[0,1] == ex3.iat[0,1] or ex5.iat[0,1] == ex2.iat[0,1] or ex5.iat[0,1] == ex1.iat[0,1]):
      upperbody(level)
    
    ex6=upper2.sample()
    if(ex6.iat[0,1] == ex5.iat[0,1] or ex6.iat[0,1] == ex4.iat[0,1] or ex6.iat[0,1] == ex3.iat[0,1] or ex6.iat[0,1] == ex2.iat[0,1] or ex6.iat[0,1] == ex1.iat[0,1]):
      upperbody(level)
    
    ex7=upper3.sample()
    if(ex7.iat[0,1] == ex6.iat[0,1] or ex7.iat[0,1] == ex5.iat[0,1] or ex7.iat[0,1] == ex4.iat[0,1] or ex7.iat[0,1] == ex3.iat[0,1] or ex7.iat[0,1] == ex2.iat[0,1] or ex7.iat[0,1] == ex1.iat[0,1]):
      upperbody(level)    

    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])

    if(level=="Intermediate" or level=="Professional"):
        ex8=upper4.sample()
        if(ex8.iat[0,1] == ex7.iat[0,1] or ex8.iat[0,1] == ex6.iat[0,1] or ex8.iat[0,1] == ex5.iat[0,1] or ex8.iat[0,1] == ex4.iat[0,1] or ex8.iat[0,1] == ex3.iat[0,1] or ex8.iat[0,1] == ex2.iat[0,1] or ex8.iat[0,1] == ex1.iat[0,1]):
         upperbody(level)
        
        ex9=upper.sample()
        if(ex9.iat[0,1] == ex8.iat[0,1] or ex9.iat[0,1] == ex7.iat[0,1] or ex9.iat[0,1] == ex6.iat[0,1] or ex9.iat[0,1] == ex5.iat[0,1] or ex9.iat[0,1] == ex4.iat[0,1] or ex9.iat[0,1] == ex3.iat[0,1] or ex9.iat[0,1] == ex2.iat[0,1] or ex9.iat[0,1] == ex1.iat[0,1]):
         upperbody(level) 
            
        ex10=upper.sample()
        if(ex10.iat[0,1] == ex9.iat[0,1] or ex10.iat[0,1] == ex8.iat[0,1] or ex10.iat[0,1] == ex7.iat[0,1] or ex10.iat[0,1] == ex6.iat[0,1] or ex10.iat[0,1] == ex5.iat[0,1] or ex10.iat[0,1] == ex4.iat[0,1] or ex10.iat[0,1] == ex3.iat[0,1] or ex10.iat[0,1] == ex2.iat[0,1] or ex10.iat[0,1] == ex1.iat[0,1]):
         upperbody(level) 
            
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])

    if(level=="Professional"):
        ex11=upper.sample()
        if(ex11.iat[0,1] == ex10.iat[0,1] or ex11.iat[0,1] == ex9.iat[0,1] or ex11.iat[0,1] == ex8.iat[0,1] or ex11.iat[0,1] == ex7.iat[0,1] or ex11.iat[0,1] == ex6.iat[0,1] or ex11.iat[0,1] == ex5.iat[0,1] or ex11.iat[0,1] == ex4.iat[0,1] or ex11.iat[0,1] == ex3.iat[0,1] or ex11.iat[0,1] == ex2.iat[0,1] or ex11.iat[0,1] == ex1.iat[0,1]):
            upperbody(level) 
            
        ex12=upper.sample()
        if(ex12.iat[0,1] == ex11.iat[0,1] or ex12.iat[0,1] == ex10.iat[0,1] or ex12.iat[0,1] == ex9.iat[0,1] or ex12.iat[0,1] == ex8.iat[0,1] or ex12.iat[0,1] == ex7.iat[0,1] or ex12.iat[0,1] == ex6.iat[0,1] or ex12.iat[0,1] == ex5.iat[0,1] or ex12.iat[0,1] == ex4.iat[0,1] or ex12.iat[0,1] == ex3.iat[0,1] or ex12.iat[0,1] == ex2.iat[0,1] or ex12.iat[0,1] == ex1.iat[0,1]):
         upperbody(level)     
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])

    upperbodyname=rs.copy()
    upperbodyForce=rs.copy()
    upperbodygeneralMuscle=rs.copy()

    upperbodyname=upperbodyname.drop(rs.columns[[ 0,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    upperbodyForce=upperbodyForce.drop(rs.columns[[ 0,1,2,3,4,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    upperbodygeneralMuscle=upperbodygeneralMuscle.drop(rs.columns[[ 0,1,2,3,4,5,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)

 #   return rs.to_numpy()

def lowerbody(level):
    global lowerbodygeneralMuscle,lowerbodyForce,lowerbodyname
    ex1=lower.sample()
    ex2=lower.sample()
    if(ex1.iat[0,1] == ex2.iat[0,1] ):
        lowerbody(level)
    
    ex3=lower.sample()
    if(ex3.iat[0,1] == ex2.iat[0,1] or ex3.iat[0,1] == ex1.iat[0,1]):
        lowerbody(level)
        
    ex4=lower.sample()
    if(ex4.iat[0,1] == ex3.iat[0,1] or ex4.iat[0,1] == ex2.iat[0,1] or ex4.iat[0,1] == ex1.iat[0,1]):
        lowerbody(level)
        
    ex5=lower.sample()
    #if(ex5.iat[0,1] == ex4.iat[0,1] or ex5.iat[0,1] == ex3.iat[0,1] or ex5.iat[0,1] == ex2.iat[0,1] or ex5.iat[0,1] == ex1.iat[0,1]):
    #    lowerbody(level)
        
    

    ex6=lower2.sample()
#     if(ex6.iat[0,0] == ex5.iat[0,0] or ex6.iat[0,0] == ex4.iat[0,0] or ex6.iat[0,0] == ex3.iat[0,0] or ex6.iat[0,0] == ex2.iat[0,0] or ex6.iat[0,0] == ex1.iat[0,0]):
#         lowerbody(level)
    
    ex7=lower.sample()
#     if(ex7.iat[0,0] == ex6.iat[0,0] or ex7.iat[0,0] == ex5.iat[0,0] or ex7.iat[0,0] == ex4.iat[0,0] or ex7.iat[0,0] == ex3.iat[0,0] or ex7.iat[0,0] == ex2.iat[0,0] or ex7.iat[0,0] == ex1.iat[0,0]):
#         lowerbody(level)

        
        
    rs=pd.concat([ex7, ex6,ex5, ex4,ex3, ex2,ex1])

    if(level=="Intermediate" or level=="Professional"):
        ex8=lower.sample()
        #if(ex8.iat[0,1] == ex7.iat[0,1] or ex8.iat[0,1] == ex6.iat[0,1] or ex8.iat[0,1] == ex5.iat[0,1] or ex8.iat[0,1] == ex4.iat[0,1] or ex8.iat[0,1] == ex3.iat[0,1] or ex8.iat[0,1] == ex2.iat[0,1] or ex8.iat[0,1] == ex1.iat[0,1]):
        #    lowerbody(level) 
            
        ex9=lower.sample()
        if(ex9.iat[0,1] == ex8.iat[0,1] or ex9.iat[0,1] == ex7.iat[0,1] or ex9.iat[0,1] == ex6.iat[0,1] or ex9.iat[0,1] == ex5.iat[0,1] or ex9.iat[0,1] == ex4.iat[0,1] or ex9.iat[0,1] == ex3.iat[0,1] or ex9.iat[0,1] == ex2.iat[0,1] or ex9.iat[0,1] == ex1.iat[0,1]):
            lowerbody(level) 
            
        ex10=lower.sample()
        #if(ex10.iat[0,1] == ex9.iat[0,1] or ex10.iat[0,1] == ex8.iat[0,1] or ex10.iat[0,1] == ex7.iat[0,1] or ex10.iat[0,1] == ex6.iat[0,1] or ex10.iat[0,1] == ex5.iat[0,1] or ex10.iat[0,1] == ex4.iat[0,1] or ex10.iat[0,1] == ex3.iat[0,1] or ex10.iat[0,1] == ex2.iat[0,1] or ex10.iat[0,1] == ex1.iat[0,1]):
        #    lowerbody(level) 
            
        rs=pd.concat([ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    if(level=="Professional"):
        ex11=lower.sample()
        if(ex11.iat[0,1] == ex10.iat[0,1] or ex11.iat[0,1] == ex9.iat[0,1] or ex11.iat[0,1] == ex8.iat[0,1] or ex11.iat[0,1] == ex7.iat[0,1] or ex11.iat[0,1] == ex6.iat[0,1] or ex11.iat[0,1] == ex5.iat[0,1] or ex11.iat[0,1] == ex4.iat[0,1] or ex11.iat[0,1] == ex3.iat[0,1] or ex11.iat[0,1] == ex2.iat[0,1] or ex11.iat[0,1] == ex1.iat[0,1]):
            lowerbody(level) 
            
        ex12=lower.sample()
        #if(ex12.iat[0,1] == ex11.iat[0,1] or ex12.iat[0,1] == ex10.iat[0,1] or ex12.iat[0,1] == ex9.iat[0,1] or ex12.iat[0,1] == ex8.iat[0,1] or ex12.iat[0,1] == ex7.iat[0,1] or ex12.iat[0,1] == ex6.iat[0,1] or ex12.iat[0,1] == ex5.iat[0,1] or ex12.iat[0,1] == ex4.iat[0,1] or ex12.iat[0,1] == ex3.iat[0,1] or ex12.iat[0,1] == ex2.iat[0,1] or ex12.iat[0,1] == ex1.iat[0,1]):
        #    lowerbody(level) 
        rs=pd.concat([ex12,ex11,ex10, ex9,ex8,ex7, ex6,ex5, ex4,ex3, ex2,ex1])
    lowerbodyname=rs.copy()
    lowerbodyForce=rs.copy()
    lowerbodygeneralMuscle=rs.copy()

    lowerbodyname=lowerbodyname.drop(rs.columns[[ 0,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    lowerbodyForce=lowerbodyForce.drop(rs.columns[[ 0,1,2,3,4,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    lowerbodygeneralMuscle=lowerbodygeneralMuscle.drop(rs.columns[[ 0,1,2,3,4,5,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)

    #return rs.to_numpy()

def fiveDay(level,d):
    global fiveDayname,fiveDayForce,fiveDaygeneralMuscle
    if(d==1):
        musels=eqEx.loc[eqEx['generalMuscle'].isin(['chest'])]
    if(d==2):
        musels=eqEx.loc[eqEx['generalMuscle'].isin(['Back','Legs and Glutes'])]

    if(d==3):
        musels=eqEx.loc[eqEx['generalMuscle'].isin(['Shoulders'])]
    if(d==4):
        musels=eqEx.loc[eqEx['generalMuscle'].isin(['core'])]
    if(d==5):
        musels=eqEx.loc[eqEx['generalMuscle'].isin(['Arm','Back,Arm'])]

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
    fiveDayForce=rs.copy()
    fiveDaygeneralMuscle=rs.copy()

    fiveDayname=fiveDayname.drop(rs.columns[[ 0,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    fiveDayForce=fiveDayForce.drop(rs.columns[[ 0,1,2,3,4,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
    fiveDaygeneralMuscle=fiveDaygeneralMuscle.drop(rs.columns[[ 0,1,2,3,4,5,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]], axis = 1)
   # return rs.to_numpy()
   
def getfullbodyName():
    str1 = "" 
    
    # traverse in the string  
    arr=fullbodyname.to_numpy()
    for ele in arr: 
        str1 += ele+"_"
        
    return str1
    
def getfullbodyForce():
    str1 = "" 
    
    arr=fullbodyForce.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1 
       

def getfullbodygeneralMuscle():
    str1 = "" 
    
    arr=fullbodygeneralMuscle.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1 
   




def getupperName():
    str1 = "" 
    
    arr=upperbodyname.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1 
   

def getupperForce():
    str1 = "" 
    
    arr=upperbodyForce.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1    

def getuppergeneralMuscle():
    str1 = "" 
    
    arr=upperbodygeneralMuscle.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1    



def getlowerName():
    str1 = "" 
    
    arr=lowerbodyname.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1    
   

def getlowerForce():
    str1 = "" 
    
    arr=lowerbodyForce.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1

def getlowergeneralMuscle():
    str1 = "" 
    
    arr=lowerbodygeneralMuscle.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1





def getfivedayName():
    str1 = "" 
    
    arr=fiveDayname.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1
   
def getfivedayForce():
    str1 = "" 
    
    arr=fiveDayForce.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1

def getfivedaymuscle():
    str1 = "" 
    
    arr=fiveDaygeneralMuscle.to_numpy()
    for ele in arr: 
        str1 += ele+"_"

    return str1

# exercises (TRUE,TRUE,TRUE,TRUE,TRUE,TRUE)
# fiveDay("Intermediate",1)

#Define a TF-IDF Vectorizer Object. Remove all english stop words such as 'the', 'a'
# tfIDf = TfIDfVectorizer(stop_words='english')
#
# #Replace NaN with an empty string
# df['targetedmuscle'] = df['targetedmuscle'].fillna('')
#
# #Construct the required TF-IDF matrix by fitting and transforming the data
# tfIDf_matrix = tfIDf.fit_transform(df['targetedmuscle'])
# cosine_sim = linear_kernel(tfIDf_matrix, tfIDf_matrix)
# indices = pd.Series(df.index, index=df['exerciseName']).drop_duplicates()

# df1=cosine_similarity(df1)
cos_sim_data = pd.DataFrame(cosine_similarity(df1))

def findAlternative1(exName,num,muscle,repeated): #,cosine_sim=cosine_sim
#     IDx = indices[exName]
#     sim_scores = list(enumerate(cosine_sim[IDx]))
#     sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
#     sim_scores = sim_scores[1:11]
#     ex_indices = [i[0] for i in sim_scores]
#     leng=len(ex_indices)
#     ex = np.empty(len(ex_indices), dtype = int)
#     for i in range(leng):
#         ex[i]=ex_indices[i]+1
  index=df.index[df['exerciseName']==exName]
  index=index[0] #exercise index to find its alternatives 
  ex =cos_sim_data.loc[index].sort_values(ascending=False).index.tolist()[0:10]
  ex_recomm =  df['exerciseName'].loc[ex].values
  for i in range(9):
    ex[i]=ex[i]+1
  RecommendedList=[]#append
  j=1
  for exercise in ex_recomm:
      k=df.index[df['exerciseName'] == exercise].tolist()
      k=k[0]
      RecommendedList.append([j,exercise,df.at[k,'Force'],df.at[k,'generalMuscle'],df.at[k,'isNeedEquipment']])
      j=j+1
#       print('The number %i recommended exercises is this one: %s \n'%(k,exercise))

  RecommendedList = pd.DataFrame(RecommendedList, columns=['ID','exerciseName','Force','generalMuscle','isNeedEquipment'])
    # print(RecommendedList)

  if num==0:
        alt=RecommendedList.loc[(RecommendedList['exerciseName']!=exName)&(RecommendedList['exerciseName']!=repeated) & (RecommendedList['isNeedEquipment']==0) & (RecommendedList['generalMuscle']==muscle)]
        if not alt.empty:
            alt=alt.iloc[0]

  if num==1:
        alt=RecommendedList.loc[(RecommendedList['exerciseName']!=exName) &(RecommendedList['exerciseName']!=repeated)& (RecommendedList['isNeedEquipment']==1) & (RecommendedList['generalMuscle']==muscle)]
        if not alt.empty:
            alt=alt.iloc[0]

  if alt.empty:
        alt=RecommendedList.loc[(RecommendedList['exerciseName']!=exName)&(RecommendedList['exerciseName']!=repeated)&(RecommendedList['generalMuscle']==muscle)]
        if not alt.empty:
           alt=alt.iloc[0]
#            if repeated==alt['exerciseName']:
#                alt=RecommendedList.loc[(RecommendedList['exerciseName']!=exName)&(RecommendedList['generalMuscle']==muscle)]
#                alt=alt.iloc[1]

#         if alt.empty:
#            alt=df.loc[(df['ID'].isin(ex)) &(df['exerciseName']!=exName)]#&(df['generalMuscle']==muscle)
#            alt=alt.iloc[3]


    
  alt=alt[['exerciseName','Force','generalMuscle']] #,,'Bench''dumbbell','Barbell','Stability Ball','Dip Machine','Pull-Up Bar','Cable Machine'
  alt=alt.to_numpy()
  str1 = ""
  for ele in alt: 
    str1 += ele+"_"

  return str1

def altEqpmnt(exName):
    eqList=''
    dfeq=df.loc[df['exerciseName'] == exName]
    dfeq=dfeq[['Bench','Dumbbell','Barbell','Stability Ball','Dip Machine','Pull-Up Bar','Cable Machine']]
    dfeq=dfeq.values.tolist()
    if dfeq[0][0]==1:
       return('Bench')
    elif dfeq[0][1]==1:
       eqList=eqList+'Dumbbell'
    elif dfeq[0][2]==1:
       eqList=eqList+'Barbell'
    elif dfeq[0][3]==1:
       eqList=eqList+'Stability Ball'
    elif dfeq[0][4]==1:
       eqList=eqList+'Dip Machine'
    elif dfeq[0][5]==1:
       eqList=eqList+'Pull-Up Bar'
    elif dfeq[0][6]==1:
       eqList=eqList+'Cable Machine'
    else:
        eqList= 'No Equipment'
    return eqList

def retreiveInstructions(exName):
    inst=inst1.loc[inst1['exerciseName']==exName]
#     return exName
    s1=inst.iloc[0,2]
    s2=inst.iloc[0,3]
    s3=inst.iloc[0,4]
    s4=inst.iloc[0,5]
    return s1+'_'+s2+'_'+s3+'_'+s4

def retreiveVideo(exName):
    inst2=inst1.loc[inst1['exerciseName']==exName]
    return inst2.iloc[0,6]

def retreiveAudio(exName):
    audio=inst1.loc[inst1['exerciseName']==exName]
    return audio.iloc[0,7]


