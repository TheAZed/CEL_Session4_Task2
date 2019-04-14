package selab.mvc.controllers.courses;

import org.json.JSONArray;
import org.json.JSONObject;
import selab.mvc.controllers.Controller;
import selab.mvc.models.DataContext;
import selab.mvc.models.DataSet;
import selab.mvc.models.entities.Course;
import selab.mvc.models.entities.CourseStudent;
import selab.mvc.models.entities.Student;
import selab.mvc.views.JsonView;
import selab.mvc.views.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CourseListController extends Controller {

    private DataSet<Course> courses;
    private DataSet<CourseStudent> courseStudentDataSet;
    public CourseListController(DataContext dataContext) {
        super(dataContext);
        courses = dataContext.getCourses();
        courseStudentDataSet = dataContext.getCourseStudents();
    }

    @Override
    public View exec(String method, InputStream body) throws IOException {
        JSONObject result = new JSONObject();
        ArrayList<Course> courseList = courses.getAll();
        ArrayList<CourseStudent> courseStudents = courseStudentDataSet.getAll();
        for (Course course :
                courseList) {
            String students = "";
            float sum = 0f;
            int count = 0;
            for (CourseStudent courseStudent :
                    courseStudents) {
                if (courseStudent.getPrimaryKey().split("S")[0].equals(course.getPrimaryKey())) {
                    students += courseStudent.getPrimaryKey().split("S")[1] + ", ";
                    sum += courseStudent.getPoint();
                    count++;
                }
            }
            if (count > 0) {
                course.setAverage(sum / count);
                course.setStudents(students.substring(0, students.length() - 2));
            } else {
                course.setAverage(0f);
                course.setStudents("-");
            }
        }
        result.put("courses", new JSONArray(courses.getAll()));
        return new JsonView(result);
    }
}
