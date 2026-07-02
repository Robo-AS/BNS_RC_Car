package org.firstinspires.ftc.teamcode.hardware;


import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

import java.util.List;

import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx;
import dev.frozenmilk.dairy.cachinghardware.CachingServo;

public class Car {
    private static Car instance = null;
    private HardwareMap hardwareMap;
    private List<LynxModule> allHubs;

    public DriveTrain driveTrain;
    public Arm arm;

    private Car(){
        driveTrain = DriveTrain.getInstance();
        arm = Arm.getInstance();
    }






    public static Car getInstance() {
        if (instance == null) {
            instance = new Car();
        }
        return instance;
    }


    public void initializeHardware(final HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;

        driveTrain.initializeHardware(hardwareMap);
        arm.initializeHardware(hardwareMap);

        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

    }


    public void initialize(){
        driveTrain.initialize();
        arm.initialize();
    }


    public void loop(double power){
        driveTrain.loop(power);
        arm.loop();
    }

    public void bulkRead(){
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
    }

}
