package selab.mvc.controllers.students;

import org.json.JSONArray;
import org.json.JSONObject;
import selab.mvc.controllers.Controller;
import selab.mvc.models.DataContext;
import selab.mvc.models.DataSet;
import selab.mvc.models.entities.CourseStudent;
import selab.mvc.models.entities.Student;
import selab.mvc.views.JsonView;
import selab.mvc.views.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StudentListController extends Controller {

    private DataSet<Student> students;
    private DataSet<CourseStudent> courseStudentDataSet;
    public StudentListController(DataContext dataContext) {
        super(dataContext);
        students = dataContext.getStudents();
        courseStudentDataSet = dataContext.getCourseStudents();
    }

    @Override
    public View exec(String method, InputStream body) throws IOException {
        JSONObject result = new JSONObject();
        ArrayList<Student> studentList = students.getAll();
        ArrayList<CourseStudent> courseStudents = courseStudentDataSet.getAll();
        for (Student student :
                studentList) {
            String courses = "";
            float sum = 0f;
            int count = 0;
            for (CourseStudent courseStudent :
                    courseStudents) {
                if (courseStudent.getPrimaryKey().split("S")[1].equals(student.getPrimaryKey())) {
                    courses += courseStudent.getPrimaryKey().split("S")[0] + ", ";
                    sum += courseStudent.getPoint();
                    count++;
                }
            }
            if (count > 0) {
                student.setAverage(sum / count);
                student.setCourses(courses.substring(0, courses.length() - 2));
            } else {
                student.setAverage(0f);
                student.setCourses("-");
            }
        }
        result.put("students", new JSONArray(students.getAll()));
        return new JsonView(result);
    }
}
