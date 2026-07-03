package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

public class IncreaseTrimCommand extends InstantCommand {
    public IncreaseTrimCommand(){
        super(
                () -> DriveTrain.getInstance().increaseTrimCounter()
        );
    }
}
