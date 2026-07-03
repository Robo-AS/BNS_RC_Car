package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import dev.frozenmilk.dairy.cachinghardware.CachingServo;



@Config
public class Arm  extends SubsystemBase {
    private static Arm instance = null;

    public CachingServo servoArm;

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    public static double servoArm_init = 0.54;
    public static double servoArm_engaged = 0.85;

    public void initializeHardware(final HardwareMap hardwareMap){
        servoArm = new CachingServo(hardwareMap.get(Servo.class, "servoArm"));
        servoArm.setDirection(Servo.Direction.FORWARD);

    }

    public void initialize(){
        servoArm.setPosition(servoArm_init);
    }

    public void loop(){

    }


    public void engageArm(){
        servoArm.setPosition(servoArm_engaged);
    }
}
