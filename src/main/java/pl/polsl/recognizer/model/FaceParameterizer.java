package pl.polsl.recognizer.model;

import org.openimaj.image.FImage;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FacialKeypoint;
import static org.openimaj.image.processing.face.detection.keypoints.FacialKeypoint.FacialKeypointType.*;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import pl.polsl.recognizer.exception.NoFaceException;
import java.awt.image.BufferedImage;
import java.util.List;

public class FaceParameterizer {

    public static Face detectFace(BufferedImage bufferedImage) throws NoFaceException {
        final int[] data = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(),
                bufferedImage.getHeight(), null, 0, bufferedImage.getWidth());
        final FImage imajImage = new FImage(data, bufferedImage.getWidth(), bufferedImage.getHeight());
        FaceDetector<KEDetectedFace, FImage> faceDetector = new FKEFaceDetector();
        List<KEDetectedFace> detectedFaces = faceDetector.detectFaces(imajImage);
        if (!detectedFaces.isEmpty()) {
            FacialKeypoint[] facialKeypoints = detectedFaces.get(0).getKeypoints();
            if (facialKeypoints.length >= 9)
                return calculateDistances(facialKeypoints);
        }
        throw new NoFaceException("No face on camera view");
    }

    private static Face calculateDistances(FacialKeypoint[] facialKeypoints) {
        double eyeLeftCenterX = (facialKeypoints[EYE_LEFT_LEFT.ordinal()].position.x
                + facialKeypoints[EYE_LEFT_RIGHT.ordinal()].position.x) / 2;
        double eyeLeftCenterY = (facialKeypoints[EYE_LEFT_LEFT.ordinal()].position.y
                + facialKeypoints[EYE_LEFT_RIGHT.ordinal()].position.y) / 2;
        double eyeRightCenterX = (facialKeypoints[EYE_RIGHT_LEFT.ordinal()].position.x
                + facialKeypoints[EYE_RIGHT_RIGHT.ordinal()].position.x) / 2;
        double eyeRightCenterY = (facialKeypoints[EYE_RIGHT_LEFT.ordinal()].position.y
                + facialKeypoints[EYE_RIGHT_RIGHT.ordinal()].position.y) / 2;
        double mouthCenterX = (facialKeypoints[MOUTH_LEFT.ordinal()].position.x
                + facialKeypoints[MOUTH_RIGHT.ordinal()].position.x) / 2;
        double mouthCenterY = (facialKeypoints[MOUTH_LEFT.ordinal()].position.y
                + facialKeypoints[MOUTH_RIGHT.ordinal()].position.y) / 2;
        double distanceEyes = Math.hypot(eyeLeftCenterX - eyeRightCenterX, eyeLeftCenterY - eyeRightCenterY);
        double distanceLeftEyeMouth = Math.hypot(eyeLeftCenterX - mouthCenterX, eyeLeftCenterY - mouthCenterY);
        double distanceRightEyeMouth = Math.hypot(eyeRightCenterX - mouthCenterX, eyeRightCenterY - mouthCenterX);
        double distanceLeftEyeNose = Math.hypot(eyeLeftCenterX - facialKeypoints[NOSE_MIDDLE.ordinal()].position.x,
                eyeLeftCenterY - facialKeypoints[NOSE_MIDDLE.ordinal()].position.y);
        double distanceRightEyeNose = Math.hypot(eyeRightCenterX - facialKeypoints[NOSE_MIDDLE.ordinal()].position.x,
                eyeRightCenterY - facialKeypoints[NOSE_MIDDLE.ordinal()].position.y);
        double distanceMouthNose = Math.hypot(mouthCenterX - facialKeypoints[NOSE_MIDDLE.ordinal()].position.x,
                mouthCenterY - facialKeypoints[NOSE_MIDDLE.ordinal()].position.y);
        double distanceEyesNose = Math.hypot((eyeLeftCenterX + eyeRightCenterX) / 2
                        - facialKeypoints[NOSE_MIDDLE.ordinal()].position.x,
                (eyeLeftCenterY + eyeRightCenterY) / 2
                        - facialKeypoints[NOSE_MIDDLE.ordinal()].position.y);
        double widthNose = Math.hypot(
                facialKeypoints[NOSE_LEFT.ordinal()].position.x - facialKeypoints[NOSE_RIGHT.ordinal()].position.x,
                facialKeypoints[NOSE_LEFT.ordinal()].position.y - facialKeypoints[NOSE_RIGHT.ordinal()].position.y);
        return new Face(distanceEyes, distanceLeftEyeMouth, distanceRightEyeMouth, distanceLeftEyeNose,
                distanceRightEyeNose, distanceMouthNose, distanceEyesNose, widthNose);
    }
}
