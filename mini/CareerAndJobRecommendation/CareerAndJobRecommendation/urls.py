from django.contrib import admin
from django.urls import path
from django.views.generic import TemplateView

from careerandjob.views import jobroleprediction, login, registration, logout, addnotification, getnotifications, \
    deletenotification, deletestudent, getmyprofile, updatestudent, updatestudentprofile, updateresume, getstudentinfo, \
    postmessage, uploadmessageaction, getmessages, deletemessages, applynotification, getapplications, \
    addnotificationaction, updatenotificationstatus, placementprediction

urlpatterns = [

    path('admin/', admin.site.urls),

    path('', TemplateView.as_view(template_name='index.html'), name='login'),
    path('login/', TemplateView.as_view(template_name='index.html'), name='login'),
    path('loginaction/', login, name='loginaction'),
    path('logout/', logout, name='logout'),

    path('jobroleprediction/', TemplateView.as_view(template_name='jobroleprediction.html'), name='predict'),
    path('jobrolepredictionaction/',jobroleprediction, name='predict'),

    path('postmessage/',postmessage, name='registration'),
    path('postmessageaction/', uploadmessageaction, name=''),
    path('getmessages/', getmessages,name=''),
    path('deletemessages/', deletemessages, name=''),

    path('registration/',TemplateView.as_view(template_name = 'registration.html'),name='registration'),
    path('regaction/',registration,name='regaction'),

    path('getstudentinfo/', getstudentinfo, name='view'),
    path('getmyprofile/', getmyprofile, name='view'),
    path('updatestudent/', updatestudent, name='update'),
    path('updatestudentprofile/', updatestudentprofile, name='update'),
    path('deletestudent/', deletestudent, name='delete'),

    path('addnotification/', addnotification, name='apply'),
    path('addnotificationaction/', addnotificationaction, name='add'),
    path('getnotifications/', getnotifications, name='view'),
    path('deletenotification/', deletenotification, name='delete'),

    path('updateresume/',updateresume,name='upload'),

    # ============================================================================================
    path('postmessage/',postmessage, name='registration'),
    path('postmessageaction/', uploadmessageaction, name=''),
    path('getmessages/', getmessages,name=''),
    path('deletemessages/', deletemessages, name=''),

    path('applynotification/', applynotification, name=''),
    path('getapplications/', getapplications, name=''),
    path('updatenotificationstatus/', updatenotificationstatus, name=''),
    path('placementprediction/',TemplateView.as_view(template_name = 'placementprediction.html'), name=''),
    path('placementpredictionaction/', placementprediction, name=''),
]
