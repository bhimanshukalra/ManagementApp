package com.example.bhimanshu_kalra.managementapp2;

public class DatabaseTeacherClass
{

    private String user;
    private String pass;
    private String subject;

    public DatabaseTeacherClass(){}

    public DatabaseTeacherClass(String user, String pass, String subject)
    {
        this.user = user;
        this.pass = pass;
        this.subject = subject;
    }

    public String getUser() {   return user;    }
    public String getPass()  {   return  pass;    }
    public String getSubject()  {   return  subject;    }

}
