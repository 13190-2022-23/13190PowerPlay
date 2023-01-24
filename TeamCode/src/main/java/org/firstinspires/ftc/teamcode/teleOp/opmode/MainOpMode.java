package org.firstinspires.ftc.teamcode.teleOp.opmode;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.teleOp.command.claw.Grab;
import org.firstinspires.ftc.teamcode.teleOp.command.claw.Release;
import org.firstinspires.ftc.teamcode.teleOp.command.drive.DriveRobotCentric;
import org.firstinspires.ftc.teamcode.teleOp.command.drive.DriveSlowMode;
import org.firstinspires.ftc.teamcode.teleOp.command.lift.ManualLift;
import org.firstinspires.ftc.teamcode.teleOp.command.lift.SetConeStack;
import org.firstinspires.ftc.teamcode.teleOp.command.lift.SetJunction;
import org.firstinspires.ftc.teamcode.teleOp.command.lift.SlowManualLift;
import org.firstinspires.ftc.teamcode.util.ConeStack;
import org.firstinspires.ftc.teamcode.util.Junction;

@TeleOp
public class MainOpMode extends BaseOpMode {
    private DriveRobotCentric robotCentricDrive;
    private DriveSlowMode slowMode;
    private ManualLift manualLift;
    private SlowManualLift slowManualLift;

    @Override
    public void initialize() {
        super.initialize();

        // drive
        robotCentricDrive = new DriveRobotCentric(drive,
                gamepadEx1::getRightX,
                gamepadEx1::getLeftY,
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));

        slowMode = new DriveSlowMode(drive,
                gamepadEx1::getRightX,
                gamepadEx1::getLeftY,
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));

        gb1(GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(slowMode);

        //claw
        gb2(GamepadKeys.Button.LEFT_BUMPER)
                .toggleWhenPressed(new Grab(claw).andThen(new SetJunction(lift, Junction.GROUND)),
                        new Release(claw).andThen(new SetJunction(lift, Junction.NONE)));

        //manual lift
        manualLift = new ManualLift(lift, gamepadEx2::getRightY);

        slowManualLift = new SlowManualLift(lift,gamepadEx2::getRightY);

        gb2(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(slowManualLift);

        //junctions
        gb2(GamepadKeys.Button.A)
                .whenPressed(new SetJunction(lift, Junction.GROUND));
        gb2(GamepadKeys.Button.X)
                .whenPressed(new SetJunction(lift, Junction.LOW));
        gb2(GamepadKeys.Button.B)
                .whenPressed(new SetJunction(lift, Junction.MEDIUM));
        gb2(GamepadKeys.Button.Y)
                .whenPressed(new SetJunction(lift, Junction.HIGH));


        //cone stack
        gb2(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new SetConeStack(lift, ConeStack.FIRST));
        gb2(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new SetConeStack(lift, ConeStack.SECOND));
        gb2(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new SetConeStack(lift, ConeStack.THIRD));
        gb2(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new SetConeStack(lift, ConeStack.FOURTH));


        //lift down
        gb2(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new SetJunction(lift, Junction.NONE));

        register(drive, lift, claw);
        drive.setDefaultCommand(robotCentricDrive);
        lift.setDefaultCommand(manualLift);
    }
}
