package com.example.diler.englishmusictube;

class Info{

     String name;
     String key;

    Info()
    {

    }



    public String getKey() {
         return key;
     }

     public void setKey(String key) {
         this.key = key;
     }

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public void setValues(Info values) {
         name=values.name;
     }
 }
