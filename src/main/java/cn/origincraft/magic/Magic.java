package cn.origincraft.magic;

import cn.origincraft.magic.cli.MagicCli;

public class Magic {
    public static void main(String[] args) throws Exception {
        try {
            int exitCode = new MagicCli().run(args);
            if (exitCode != 0) {
                System.exit(exitCode);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        } catch (Exception e) {
            System.err.println(e.getMessage() == null ? e.toString() : e.getMessage());
            System.exit(1);
        }
    }
}
