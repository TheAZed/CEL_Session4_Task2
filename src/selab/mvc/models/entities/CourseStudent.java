package selab.mvc.models.entities;/*
 * Created by A.Zed on 4/14/2019.
 */

import selab.mvc.models.Model;

public class CourseStudent implements Model {
    private String courseNo;
    private String studentNo;
    private String point;

    public CourseStudent(String courseNo, String studentNo) {
        this.courseNo = courseNo;
        this.studentNo = studentNo;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public float getPoint(){
        return Float.parseFloat(point);
    }

    @Override
    public String getPrimaryKey() {
        return courseNo + studentNo;
    }
}
