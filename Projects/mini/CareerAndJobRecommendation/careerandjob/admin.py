from django.contrib import admin

# Register your models here.
from careerandjob.models import NotificationModel, StudentModel, MessageModel, ApplicationModel

admin.site.register(NotificationModel)
admin.site.register(MessageModel)
admin.site.register(StudentModel)
admin.site.register(ApplicationModel)