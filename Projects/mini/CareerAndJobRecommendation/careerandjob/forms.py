from django.forms import Form, CharField, PasswordInput,FileField


class StudentForm(Form):

    rno=CharField(max_length=50)
    password=CharField(max_length=50)
    name=CharField(max_length=50)
    email=CharField(max_length=50)
    mobile=CharField(max_length=50)
    branch=CharField(max_length=50)
    gender=CharField(max_length=50)
    dob=CharField(max_length=50)
    passing=CharField(max_length=50)

    resume=FileField()
    isHavingBacklogs=CharField(max_length=50)
    knownLanguages=CharField(max_length=50)
    ishavingyeargap=CharField(max_length=50)

class LoginForm(Form):
    username = CharField(max_length=100)
    password = CharField(widget=PasswordInput())

class MessageForm(Form):
    message=CharField(max_length=500)
    postedto=CharField(max_length=500)

class UpdateStudentForm(Form):
    email=CharField(max_length=50)
    mobile=CharField(max_length=50)
    isHavingBacklogs=CharField(max_length=50)
    knownLanguages=CharField(max_length=50)

class UpdateResumeForm(Form):
    resume = FileField()


