package org.firstinspires.ftc.teamcode.opmodes;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.hardware.Car;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOpCar🚗", group = "OpModes")
public class TeleOpCar extends CommandOpMode {
    private final Car car = Car.getInstance();
    public GamepadEx gamepadEx;
    private double loopTime = 0;


    @Override
    public void initialize() {
        gamepad1.setLedColor(128, 0, 128, Gamepad.LED_DURATION_CONTINUOUS);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        CommandScheduler.getInstance().reset();
        gamepadEx = new GamepadEx(gamepad1);
        car.initializeHardware(hardwareMap);
        car.initialize();

    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        car.bulkRead();

        car.loop(gamepad1.left_stick_y);


        telemetry.addData("ceva", "ceva");
        double loop = System.nanoTime();
        telemetry.addData("Hz", 1000000000 / (loop - loopTime));
        loopTime = loop;
        telemetry.update();

    }
}
