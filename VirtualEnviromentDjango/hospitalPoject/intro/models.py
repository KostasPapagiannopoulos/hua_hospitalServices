from django.db import models

class Intro(models.Model):
    title = models.CharField (max_length = 140)
    body = models.TextField()
   

    def __unicode__(self):
        return self.title

# Create your models here.
