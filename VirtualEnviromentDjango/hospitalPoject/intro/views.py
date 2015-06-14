from django.shortcuts import render
from django.http import HttpResponse
from pydoc import render_doc

def newsFeed(request):
    return HttpResponse (request, 'intro.html')

# Create your views here.
