package pl.polsl.recognizer.database;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import pl.polsl.recognizer.model.Face;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {

    MongoClient mongoClient;

    MongoDatabase database;

    MongoCollection<Document> facesCollection;

    private static DatabaseConnector ourInstance = new DatabaseConnector();

    public static DatabaseConnector getInstance() {
        return ourInstance;
    }

    private DatabaseConnector() {
        mongoClient = new MongoClient();
        try {
            database = mongoClient.getDatabase("intelligent_face_recognizer");
            facesCollection = database.getCollection("faces");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFace(Face face) {
        Document document = new Document()
                .append("name", face.getName())
                .append("distance_eyes", face.getDistanceEyes())
                .append("distance_left_eye_mouth", face.getDistanceLeftEyeMouth())
                .append("distance_right_eye_mouth", face.getDistanceRightEyeMouth())
                .append("distance_left_eye_nose", face.getDistanceLeftEyeNose())
                .append("distance_right_eye_nose", face.getDistanceRightEyeNose())
                .append("distance_mouth_nose", face.getDistanceMouthNose())
                .append("distance_eyes_nose", face.getDistanceEyesNose())
                .append("width_nose", face.getWidthNose());
        facesCollection.insertOne(document);
    }

    public List<Face> getAllFaces() {
        List<Face> faces = new ArrayList<Face>();
        FindIterable<Document> find = facesCollection.find();
        MongoCursor<Document> cursor = find.iterator();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                faces.add(new Face(
                        (String)document.get("name"),
                        (Double)document.get("distance_eyes"),
                        (Double)document.get("distance_left_eye_mouth"),
                        (Double)document.get("distance_right_eye_mouth"),
                        (Double)document.get("distance_left_eye_nose"),
                        (Double)document.get("distance_right_eye_nose"),
                        (Double)document.get("distance_mouth_nose"),
                        (Double)document.get("distance_eyes_nose"),
                        (Double)document.get("width_nose")
                ));
            }
        } finally {
            cursor.close();
        }
        return faces;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public MongoCollection<Document> getFacesCollection() {
        return facesCollection;
    }

    public void setFacesCollection(MongoCollection<Document> facesCollection) {
        this.facesCollection = facesCollection;
    }
}
