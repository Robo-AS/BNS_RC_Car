package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import dev.frozenmilk.dairy.cachinghardware.CachingServo;



@Config
public class Arm  extends SubsystemBase {
    private static Arm instance = null;

    public CachingServo servoElbow, servoShoulder;

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    public void initializeHardware(final HardwareMap hardwareMap){
        servoElbow = new CachingServo(hardwareMap.get(Servo.class, "servoElbow"));
        servoElbow.setDirection(Servo.Direction.FORWARD);

        servoShoulder = new CachingServo(hardwareMap.get(Servo.class, "servoShoulder"));
        servoShoulder.setDirection(Servo.Direction.FORWARD);
    }

    public void initialize(){

    }

    public void loop(){

    }
}
