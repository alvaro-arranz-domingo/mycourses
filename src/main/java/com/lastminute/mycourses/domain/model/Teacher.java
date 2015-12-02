package com.lastminute.mycourses.domain.model;

/**
 * Created by administrator on 1/12/15.
 */
public class Teacher {

    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        return !(name != null ? !name.equals(teacher.name) : teacher.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
