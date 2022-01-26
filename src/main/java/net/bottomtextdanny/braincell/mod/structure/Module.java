package net.bottomtextdanny.braincell.mod.structure;

public interface Module {

    void activate();

    boolean isActive();

    static Module makeSimple(SideConfig sideConfiguration) {
        return new Module.Simple(sideConfiguration);
    }

    class Simple implements Module {
        private boolean activated;
        private final SideConfig side;

        private Simple(SideConfig side) {
            super();
            this.side = side;
        }

        @Override
        public void activate() {
            if (this.side.test()) this.activated = true;
        }

        @Override
        public boolean isActive() {
            return this.activated;
        }
    }
}
