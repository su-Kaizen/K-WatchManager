import os

class c:
    green = '\033[92m'
    red = '\033[91m'
    end = '\033[0m'

def start_git_flow(b,c):
    f = open('config.txt').readlines()[1]
    os.chdir(f)
    os.system('git add .')
    c1 = 'git commit -m '+c
    os.system(c1)
    os.system('git checkout main')
    os.system('git pull --rebase origin main')
    c2 = 'git merge --no-ff '+b
    os.system(c2)
    os.system('git push origin main')

os.system('cls')
print('················· EZ GIT ··················')
print("Type the name of the branch that you are working on: ",end="")
branch_name = input()
commit_message = '"'+branch_name.replace('/','(')+'): '
print("Type the commit message: ",end="")
commit_message += input()+'"'

start_git_flow(branch_name,commit_message)
print(c.green+'\nFinish!'+c.end)