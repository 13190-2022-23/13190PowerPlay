package org.firstinspires.ftc.teamcode.teleOp.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.LiftSubsystem;

import java.util.function.DoubleSupplier;

public class ManualLift extends CommandBase {
    private final LiftSubsystem lift;
    private final DoubleSupplier input;

    public ManualLift(LiftSubsystem lift, DoubleSupplier input) {
        this.lift = lift;
        this.input = input;
        addRequirements(lift);
    }

    @Override
    public void execute() {
        if(input.getAsDouble() != 0){
            lift.manualLift(input.getAsDouble());
            lift.stable();
        }
        else{
            lift.stable();
        }

    }

   @Override
   public boolean isFinished() {
       return false;
    }
}
