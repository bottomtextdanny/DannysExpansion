package net.bottomtextdanny.dannys_expansion.core.Util.animation;

import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;

/*
https://easings.net/#
*/
public abstract class Easing {

    public static final Easing LINEAR = new Easing() {
        public float progression(float value) {
            return  value;
        }
    };
    public static final Easing EASE_IN_GAMMA = new Easing() {
        public float progression(float value) {
            return (float) DEMath.pow(value, 2.2F);
        }
    };
    public static final Easing EASE_OUT_GAMMA = new Easing() {
        public float progression(float value) {
            return (float) (1 - DEMath.pow(1 - value, 2.2F));
        }
    };
    public static final Easing EASE_IN_CUBIC = new Easing() {
        public float progression(float value) {
            return  value * value * value;
        }
    };
    public static final Easing EASE_OUT_CUBIC = new Easing() {
        public float progression(float value) {
            return (float) (1 - DEMath.pow(1 - value, 3));
        }
    };
    public static final Easing EASE_BOTH_CUBIC = new Easing() {
        public float progression(float value) {
            return value <= 0.5 ? value * value * value : (float) (1 - DEMath.pow(1 - value, 3));
        }
    };

    public static final Easing EASE_IN_SQUARE = new Easing() {
        public float progression(float value) {
            return  value * value;
        }
    };
    public static final Easing EASE_OUT_SQUARE = new Easing() {
        public float progression(float value) {return  (float) (1 - DEMath.pow(1 - value, 2));
        }
    };

    public static final Easing EASE_IN_OUT_BACK = new Easing() {
        public float progression(float value) {
            double c4 = 2 * Math.PI / 3;

            return value == 0 ? 0 : (float) (value == 1 ? 1 : DEMath.pow(2, -10 * value) * Math.sin((value * 10 - 0.75) * c4) + 1);
        }
    };

    public static final Easing BOUNCE_OUT = new Easing() {
        public float progression(float value) {
            double n1 = 7.5625;
            double d1 = 2.75;

            if (value < 1 / d1) {
                return (float) (n1 * value * value);
            } else if (value < 2 / d1) {
                return (float) (n1 * (value -= 1.5 / d1) * value + 0.75);
            } else if (value < 2.5 / d1) {
                return (float) (n1 * (value -= 2.25 / d1) * value + 0.9375);
            } else {
                return (float) (n1 * (value -= 2.625 / d1) * value + 0.984375);
            }
        }
    };
	
	public static final Easing EASE_IN_BACK = new Easing() {
		public float progression(float value) {
			double c1 = 1.70158;
			double c3 = c1 + 1;
			
			return (float) (1 + c3 * DEMath.pow(value - 1, 3) + c1 * DEMath.pow(value - 1, 2));
		}
	};

    public static final Easing EASE_OUT_BACK = new Easing() {
        public float progression(float value) {
            double c1 = 1.70158;
            double c3 = c1 + 1;
	
	        return (float) (c3 * value * value * value - c1 * value * value);
        }
    };

    public static final Easing EASE_IN_OUT_SINE = new Easing() {
        public float progression(float value) {
            return -(DEMath.cos(Math.PI * value) - 1) / 2;
        }
    };

    public static final Easing EASE_IN_SINE = new Easing() {
        public float progression(float value) {
            return 1 - DEMath.cos((float) (value * Math.PI / 2));
        }
    };

    public static final Easing EASE_OUT_SINE = new Easing() {
        public float progression(float value) {
            return DEMath.sin((float) (value * Math.PI / 2));
        }
    };


    public abstract float progression(float value);
}
