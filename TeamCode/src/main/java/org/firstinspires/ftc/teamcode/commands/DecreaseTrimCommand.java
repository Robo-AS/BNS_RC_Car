package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

public class DecreaseTrimCommand extends InstantCommand {
    public DecreaseTrimCommand(){
        super(
                () -> DriveTrain.getInstance().decreaseTrimCounter()
        );
    }
}
