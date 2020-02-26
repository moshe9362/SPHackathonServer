package hackathon.server.dal;

public class DBInserter {

    public void insertExerciseRecord(ExerciseRecordRequest jsonEntity) {
        System.out.println("Saved record to DB");

    }

    public void insertExcelData(ExcelData jsonEntity) {
        System.out.println("Saved ExcelData in DB");
    }
}