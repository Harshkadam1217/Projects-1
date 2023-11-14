from django.shortcuts import render

from careerandjob.forms import LoginForm, MessageForm, UpdateResumeForm, UpdateStudentForm, StudentForm
from careerandjob.models import NotificationModel, MessageModel, ApplicationModel, StudentModel

import pandas as pd
from sklearn.preprocessing import LabelEncoder
import os
from datetime import datetime
import pickle

PROJECT_PATH = os.path.abspath(os.path.dirname(__name__))
model_placement = pickle.load(open(PROJECT_PATH+'/models/sv_model.pkl', 'rb'))
model_salary = pickle.load(open(PROJECT_PATH+'/models/rfreg_model.pkl', 'rb'))

import smtplib

def updatenotificationstatus(request):

    userid=request.GET['userid']
    job = request.GET['job']
    student=StudentModel.objects.filter(rno=userid).first()

    try:
        s = smtplib.SMTP("smtp.gmail.com", 587)  # 587 is a port number
        s.starttls()
        s.login("kadamharsh2002@gmail.com", "vwwamorehoqlbiuu")

        s.sendmail("kadamharsh2002@gmail.com",student.email,"your are sort listed for the position "+job)
        s.quit()

    except Exception as e:
        print("Send OTP via Email","Please enter the valid email address OR check an internet connection")

    applications = dict()

    for application in ApplicationModel.objects.all():
        notification = NotificationModel.objects.get(id=application.notificationid)
        applications.update({application.userid: notification.companyname + "_" + notification.jobrole})

    return render(request, "applications.html", {"applications": applications})

def registration(request):

    if request.method == "POST":

        # Get the posted form
        registrationForm = StudentForm(request.POST,request.FILES)

        if registrationForm.is_valid():

            regModel = StudentModel()

            regModel.name = registrationForm.cleaned_data["name"]
            regModel.email = registrationForm.cleaned_data["email"]
            regModel.mobile = registrationForm.cleaned_data["mobile"]
            regModel.rno = registrationForm.cleaned_data["rno"]
            regModel.password = registrationForm.cleaned_data["password"]
            regModel.branch = registrationForm.cleaned_data["branch"]

            regModel.gender = registrationForm.cleaned_data["gender"]
            regModel.dob = registrationForm.cleaned_data["dob"]
            regModel.passing = registrationForm.cleaned_data["passing"]
            regModel.resume = registrationForm.cleaned_data["resume"]
            regModel.isHavingBacklogs = registrationForm.cleaned_data["isHavingBacklogs"]
            regModel.knownLanguages = registrationForm.cleaned_data["knownLanguages"]
            regModel.ishavingyeargap = registrationForm.cleaned_data["ishavingyeargap"]

            user = StudentModel.objects.filter(rno=regModel.rno).first()

            if user is not None:
                render(request, 'registration.html', {"message": "User All Ready Exist"})
            else:
                regModel.save()
                return render(request, 'registration.html', {"message": "Registred Sucessfully"})
        else:
            return render(request, 'registration.html', {"message": "Invalid Form"})

    return render(request, 'registration.html', {"message": "Invalid Request"})

def login(request):

    if request.method == "GET":
        # Get the posted form
        loginForm = LoginForm(request.GET)

        if loginForm.is_valid():

            uname = loginForm.cleaned_data["username"]
            upass = loginForm.cleaned_data["password"]

            if uname == "admin" and upass == "admin":
                request.session['role'] = "admin"
                request.session['username'] = uname

                return render(request, "viewnotifications.html", {"notifications": NotificationModel.objects.all()})

            else:

                user = StudentModel.objects.filter(rno=uname, password=upass).first()

                if user is not None:

                    request.session['role'] = "student"
                    request.session['username'] = uname

                    return render(request, "jobroleprediction.html")

                else:
                    return render(request, 'index.html', {"message": "Invalid Credentials"})
        else:
            return render(request, 'index.html', {"message": "Invalid From"})

    return render(request, 'index.html', {"message": "Invalid Request"})

def logout(request):
    try:
        del request.session['username']
    except:
        pass
    return render(request, 'index.html', {})

def getmyprofile(request):

    studentid = request.session['username']

    student=StudentModel.objects.filter(rno=studentid).first()
    docloc = student.resume
    tokens = str(docloc).split("/")
    student.resume = tokens[1]

    return render(request, "viewmyprofile.html", {"student":student})


def getstudentinfo(request):

    studentid = request.GET['rno']

    student=StudentModel.objects.filter(rno=studentid).first()

    if student is not None:
        docloc = student.resume
        tokens = str(docloc).split("/")
        student.resume = tokens[1]

        return render(request, "viewstudentprofile.html", {"student": student})

    else:
        return render(request, "viewstudents.html", {})


def updatestudent(request):
    studentid = request.session['username']
    return render(request, "updateprofile.html", {"student":StudentModel.objects.filter(rno=studentid).first()})

def updatestudentprofile(request):

    updateStudentForm = UpdateStudentForm(request.POST,request.FILES)

    if updateStudentForm.is_valid():

        email = updateStudentForm.cleaned_data["email"]
        mobile = updateStudentForm.cleaned_data["mobile"]
        isHavingBacklogs = updateStudentForm.cleaned_data["isHavingBacklogs"]
        knownLanguages = updateStudentForm.cleaned_data["knownLanguages"]

        StudentModel.objects.filter(rno=request.session['username']).update(email=email,mobile=mobile,isHavingBacklogs=isHavingBacklogs,knownLanguages=knownLanguages)

        studentid = request.session['username']
        return render(request, "viewmyprofile.html", {"student":StudentModel.objects.filter(rno=studentid)})

def deletestudent(request):

    studentid= request.GET['studentid']
    StudentModel.objects.get(rno=studentid).delete()

    return render(request, "viewstudents.html", {"students": StudentModel.objects.all()})

def updateresume(request):

    if request.method == "POST":

        updateresumefrom = UpdateResumeForm(request.POST,request.FILES)

        if updateresumefrom.is_valid():

            student = StudentModel.objects.filter(rno=request.session['username']).first()

            student1=student
            student1.resume=updateresumefrom.cleaned_data['resume']

            StudentModel.objects.filter(rno=request.session['username']).delete()
            student1.save()

            studentid = request.session['username']
            student = StudentModel.objects.filter(rno=studentid).first()
            docloc = student.resume
            tokens = str(docloc).split("/")
            student.resume = tokens[1]
            return render(request, "viewmyprofile.html", {"student": student})

#============================================================================

def addnotification(request):

    dataset = pd.read_csv(PROJECT_PATH+"/dataset/career_compute_dataset.csv")
    jobroles = set(dataset["ROLE"].tolist())
    print(jobroles)
    return render(request, 'addnotification.html', {"jobroles":set(jobroles)})

def addnotificationaction(request):

    companyname = request.GET['companyname']
    jobrole = request.GET['jobrole']
    location = request.GET['location']
    salary = request.GET['salary']
    applylink = request.GET['applylink']
    description = request.GET['description']
    lastdate = request.GET['lastdate']

    NotificationModel(companyname=companyname,jobrole=jobrole,location=location,salary=salary,applylink=applylink,description=description,lastdate=lastdate).save()

    return render(request, 'addnotification.html', {"message": "Job Posted SuccessFully"})

    return render(request, 'addnotification.html', {"message": "Job Posted Failed"})

def getnotifications(request):
    return render(request, "viewnotifications.html",{"notifications": NotificationModel.objects.all()})

def deletenotification(request):

    notificationid = request.GET['notificationid']
    NotificationModel.objects.get(id=notificationid).delete()

    return render(request, "viewnotifications.html", {"notifications": NotificationModel.objects.all()})

#======================================================

def jobroleprediction(request):

    sslc = int(request.POST["sslc"])
    hsc = int(request.POST["hsc"])
    cgpa = int(request.POST["cgpa"])
    school_type = int(request.POST["school_type"])
    no_of_miniprojects = int(request.POST["no_of_miniprojects"])
    no_of_projects = int(request.POST["no_of_projects"])
    coresub_skill = int(request.POST["coresub_skill"])
    aptitude_skill = int(request.POST["aptitude_skill"])
    problemsolving_skill = int(request.POST["problemsolving_skill"])
    programming_skill = int(request.POST["programming_skill"])
    abstractthink_skill = int(request.POST["abstractthink_skill"])
    design_skill = int(request.POST["design_skill"])
    lab_programs = int(request.POST["lab_programs"])
    ds_coding = int(request.POST["ds_coding"])
    technology_used = int(request.POST["technology_used"])
    sympos_attend = int(request.POST["sympos_attend"])
    sympos_won = int(request.POST["sympos_won"])
    extracurricular = int(request.POST["extracurricular"])
    learning_style = int(request.POST["learning_style"])
    college_performence = int(request.POST["college_performence"])
    college_skills = int(request.POST["college_skills"])

    dataset = pd.read_csv(PROJECT_PATH+"/dataset/career_compute_dataset.csv")
    labelencoder = LabelEncoder()
    df = dataset
    label = df.iloc[:49, -1]
    original = label.unique()
    label = label.values
    label2 = labelencoder.fit_transform(label)
    y = pd.DataFrame(label2, columns=["ROLE"])
    numeric = y["ROLE"].unique()
    y1 = pd.DataFrame({'ROLE': original, 'Associated Number': numeric})
    print(y1)

    import pickle
    loaded_model = pickle.load(open(PROJECT_PATH+"/model/final_model.pkl", "rb"))

    cols_when_model_builds = loaded_model.get_booster().feature_names
    print(cols_when_model_builds)

    x_new = [sslc, hsc, cgpa, school_type, no_of_miniprojects,
       no_of_projects, coresub_skill, aptitude_skill,
       problemsolving_skill, programming_skill, abstractthink_skill,
       design_skill, lab_programs, ds_coding, technology_used,
       sympos_attend, sympos_won, extracurricular, learning_style,
       college_performence, college_skills]

    l = ['f0', 'f1', 'f2', 'f3', 'f4', 'f5', 'f6', 'f7', 'f8', 'f9', 'f10', 'f11', 'f12', 'f13', 'f14', 'f15', 'f16',
         'f17', 'f18', 'f19', 'f20']
    df = pd.DataFrame([x_new],columns=l)

    new_pred = loaded_model.predict(df)
    label=y1.loc[y1['Associated Number'] == new_pred[0], 'ROLE'].tolist()[0]
    print("Label :",label)

    return render(request, "jobroleprediction.html", {"notifications": NotificationModel.objects.filter(jobrole=label),
    "result":"Suggested Job Role : {}".format(label)})

#====================================================================================

def postmessage(request):
    postedto = request.GET['postedto']
    return render(request, "postmessage.html", {"postedto":postedto})

def uploadmessageaction(request):

    messageForm = MessageForm(request.GET)
    uname = request.session['username']

    if messageForm.is_valid():

        message = messageForm.cleaned_data['message']
        postedto = messageForm.cleaned_data['postedto']

        postedby = uname
        mdate = datetime.now()

        MessageModel(message=message,postedby=postedby,postedto=postedto,mdate=mdate).save()

        return render(request, "messages.html", {"messages":MessageModel.objects.filter(postedto=uname)})

    else:
        return render(request, "postmessage.html", {"messages":"invalid form"})

def getmessages(request):
    uname = request.session['username']
    return render(request, "messages.html", {"messages": MessageModel.objects.filter(postedto=uname)})

def deletemessages(request):

    messageid=request.GET['messageid']

    MessageModel.objects.get(id=messageid).delete()

    uname = request.session['username']
    return render(request, "messages.html", {"messages": MessageModel.objects.filter(postedto=uname)})

#======================================================================================

def applynotification(request):
    ApplicationModel(userid=request.session['username'],notificationid=request.GET['nid'],status="pending").save()
    return render(request, "viewnotifications.html",{"message":"applied successfully","notifications": NotificationModel.objects.all()})

def getapplications(request):

    applications=dict()

    for application in ApplicationModel.objects.all():
        notification=NotificationModel.objects.get(id=application.notificationid)
        applications.update({application.userid:notification.companyname+"_"+notification.jobrole})

    return render(request, "applications.html", {"applications":applications})


def placementprediction(request):
    gender = int(request.GET["gender"])
    ssc_p = int(request.GET["ssc_p"])
    ssc_b = int(request.GET["ssc_b"])
    hsc_p = int(request.GET["hsc_p"])
    hsc_b = int(request.GET["hsc_b"])
    degree_p = int(request.GET["degree_p"])
    workex = int(request.GET["workex"])
    etest_p = int(request.GET["etest_p"])
    degree = request.GET["degree_t"]

    Comm_Mgmt = 0
    Other_Degree = 0
    Sci_Tech = 0

    if degree == "Comm&Mgmt":
        Comm_Mgmt = 1
        Other_Degree = 0
        Sci_Tech = 0
    elif degree == "Others":
        Comm_Mgmt = 0
        Other_Degree = 1
        Sci_Tech = 0
    elif degree == "Sci&Tech":
        Comm_Mgmt = 0
        Other_Degree = 0
        Sci_Tech = 1

    hsc = request.GET["hsc_s"]

    print("HSC:", hsc)

    Arts = 0
    Commerce = 0
    Science = 0

    if hsc == "Arts":
        Arts = 1
        Commerce = 0
        Science = 0
    elif hsc == "Commerce":
        Arts = 0
        Commerce = 1
        Science = 0
    elif hsc == "Science":
        Arts = 0
        Commerce = 0
        Science = 1

    print([gender, ssc_p, ssc_b, hsc_p, hsc_b, degree_p, workex, etest_p, Arts, Commerce, Science, Comm_Mgmt,
           Other_Degree, Sci_Tech])

    prediction = model_placement.predict([[gender, ssc_p, ssc_b, hsc_p, hsc_b, degree_p, workex, etest_p, Arts,
                                           Commerce, Science, Comm_Mgmt, Other_Degree, Sci_Tech]])

    output = ""
    print("Prediction:", prediction)
    if prediction[0] == 0:
        output = "Not Placed"
    elif prediction[0] == 1:
        output = "Likely to be Placed"

    salary = model_salary.predict([[gender, ssc_p, ssc_b, hsc_p, hsc_b, degree_p, workex, etest_p, Arts, Commerce,
                                    Science, Comm_Mgmt, Other_Degree, Sci_Tech]])
    salaryhigh = salary + 100000
    salarylow = salary - 100000

    if output == "Not Placed":
        salaryhigh = 0
        salarylow = 0

    return render(request, "placementprediction.html", {"prediction_text":output,"salary_high":salaryhigh,"salary_low":salarylow})