public class ImpStudentInfo {

    private String ra;
    private String name;
    private String courseName;

    public ImpStudentInfo(String ra, String name, String courseName) {
        this.ra = ra;
        this.name = name;
        this.courseName = courseName;
    }

    public String getRa() {
        return ra;
    }

    public String getName() {
        return name;
    }

    public String getCourseName() {
        return courseName;
    }
}
