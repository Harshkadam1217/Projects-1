from django.db import models

from django.db.models import Model

class NotificationModel(Model):

    companyname=models.CharField(max_length=50)
    jobrole = models.CharField(max_length=50)
    location = models.CharField(max_length=50)
    salary = models.CharField(max_length=50)
    applylink=models.CharField(max_length=50)
    description = models.CharField(max_length=50)
    lastdate = models.CharField(max_length=50)

class StudentModel(Model):
    rno=models.CharField(max_length=50)
    password=models.CharField(max_length=50)
    name=models.CharField(max_length=50)
    email=models.CharField(max_length=50)
    mobile=models.CharField(max_length=50)
    branch=models.CharField(max_length=50)
    gender=models.CharField(max_length=50)
    dob=models.CharField(max_length=50)
    passing=models.CharField(max_length=50)
    resume=models.FileField(upload_to="documents")
    isHavingBacklogs=models.CharField(max_length=50)
    knownLanguages=models.CharField(max_length=50)
    ishavingyeargap=models.CharField(max_length=50)

class MessageModel(Model):
    message=models.CharField(max_length=500)
    postedby=models.CharField(max_length=500)
    postedto=models.CharField(max_length=500)
    mdate=models.CharField(max_length=500)

    class Meta:
        db_table = "messages"

class ApplicationModel(Model):
    userid=models.FileField(upload_to="documents")
    notificationid=models.CharField(max_length=50)
    status=models.CharField(max_length=50)