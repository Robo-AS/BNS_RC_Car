package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class EngageArm extends InstantCommand {
    public EngageArm(){
        super(
                () -> Arm.getInstance().engageArm()
        );
    }
}
