package selab.mvc.controllers;

import org.json.JSONObject;
import selab.mvc.models.DataContext;
import selab.mvc.models.DataSet;
import selab.mvc.models.entities.Course;
import selab.mvc.models.entities.CourseStudent;
import selab.mvc.models.entities.Student;
import selab.mvc.views.JsonView;
import selab.mvc.views.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddStudentToCourseController extends Controller {

    public AddStudentToCourseController(DataContext dataContext) {
        super(dataContext);
    }

    @Override
    public View exec(String method, InputStream body) throws Exception {
        if (!method.equals("POST"))
            throw new IOException("Method not supported");

        JSONObject input = readJson(body);
        String studentNo = input.getString("studentNo");
        String courseNo = input.getString("courseNo");
        String points = input.getString("points");

        // TODO: Add required codes to associate the student with course
        Course course = this.dataContext.getCourses().get(courseNo);
        if (course == null)
            throw new Exception("Course not available!");
        Student student = this.dataContext.getStudents().get(studentNo);
        if (student == null)
            throw new Exception("Student not available!");
        CourseStudent courseStudent = new CourseStudent(courseNo, studentNo);
        courseStudent.setPoint(points);
        this.dataContext.getCourseStudents().add(courseStudent);

        Map<String, String> result = new HashMap<>();
        result.put("success", "true");
        return new JsonView(new JSONObject(result));
    }
}
