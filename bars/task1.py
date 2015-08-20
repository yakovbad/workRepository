import random
import os
import re


# please write Path
path = "" 


extensions = [".txt", ".dat", ".src"]

def createFolder(path):
	os.mkdir(path, 0777)
	pass
# this function create 1-30 file in Path
def createFile():
	os.chdir(path);
	for x in xrange(1,30):
		a = '%d' %(random.randint(10,50)) + random.choice(extensions)
		file = open(a, 'w').close()
		pass
	pass
#this function create more 30 file in Path
def createFileTwo(path):
	os.chdir(path);
	listfile = os.listdir(path)
	for x in xrange(1,30):
		a = '%d' %(random.randint(10,50)) + random.choice(extensions)
		if listfile.count(a) == 0 :
				file = open(a, 'w')
				file.write('new')
				file.close
		else :
				file = open(a, "w")
				file.write("old")
				file.close
		pass
		pass
	pass
#this function rename file with ".txt" to ".src"
def renameFile(path):
	os.chdir(path);
	listfile = os.listdir(path)
	files = []

	for x in xrange(len(listfile)):
		a = re.search(r"1..txt", listfile[x])
		if a != None :
			files.append(a.group(0))
		pass

	for x in xrange(len(files)):
		e = files[x][0:2] + ".src"
		if listfile.count(e) == 0 :
			os.rename(files[x], e)
		else :
			os.remove(path + e)
			os.rename(files[x], e)
		pass
	pass
# this function create a dictionary
def createDict():
	os.chdir(path);
	listfiles = os.listdir(path)
	listNew = []
	listOld = []
	listfile = []

	for x in xrange(len(listfiles)):
		file = open(listfiles[x], "r")
		f = file.read()
		if f == "new" :
			listNew.append(listfiles[x])
		elif f == "old" :
			listOld.append(listfiles[x])
		else :
			listfile.append(listfiles[x])
		pass

	dictList = {'new' : listNew, 'old' : listOld, '""' : listfile}
	return dictList
	pass

if path == "" :
	path = os.getcwd() + "/";
createFolder(path)
createFile();
createFileTwo(path)
renameFile(path)
d = createDict()
print d
print "finish"