package bottomtextdanny.de_json_generator;

import bottomtextdanny.de_json_generator.jsonBakers._model.JsonModel;

public class ExtraModelGen extends GenUtils{

	public static JsonModel m_custom_cube(String particle,
                                          String north,
                                          String south,
                                          String east,
                                          String west,
                                          String up,
                                          String down
										) {
		return new JsonModel("minecraft:block/cube", map()
				.put("particle", particle)
				.put("north", north)
				.put("south", south)
				.put("east", east)
				.put("west", west)
				.put("up", up)
				.put("down", down)
		);
	}

	public static JsonModel m_cube_all(String all) {
		return new JsonModel("minecraft:block/cube_all", map()
				.put("all", all)
		);
	}

	public static JsonModel m_stairs(String bottom, String top, String side) {
		return new JsonModel("minecraft:block/stairs", map()
				.put("bottom", bottom)
				.put("top", top)
				.put("side", side)
		);
	}

	public static JsonModel m_stairs_inner(String bottom, String top, String side) {
		return new JsonModel("minecraft:block/stairs_inner", map()
				.put("bottom", bottom)
				.put("top", top)
				.put("side", side)
		);
	}

	public static JsonModel m_stairs_outer(String bottom, String top, String side) {
		return new JsonModel("minecraft:block/stairs_outer", map()
				.put("bottom", bottom)
				.put("top", top)
				.put("side", side)
		);
	}

	public static JsonModel m_slab(String bottom, String top, String side) {
		return new JsonModel("minecraft:block/slab", map()
				.put("bottom", bottom)
				.put("top", top)
				.put("side", side)
		);
	}

	public static JsonModel m_slab_top(String bottom, String top, String side) {
		return new JsonModel("minecraft:block/slab_top", map()
				.put("bottom", bottom)
				.put("top", top)
				.put("side", side)
		);
	}
	
	public static JsonModel m_potted_plant(String plant) {
		return new JsonModel("minecraft:block/flower_pot_cross",
			map().put("plant", plant)
		);
	}
	
	public static JsonModel m_wall_inventory(String wall) {
		return new JsonModel("minecraft:block/wall_inventory",
			map().put("wall", wall)
		);
	}

	public static JsonModel m_wall_post(String wall) {
		return new JsonModel("minecraft:block/template_wall_post",
			map().put("wall", wall)
		);
	}
	
	public static JsonModel m_wall_side(String wall) {
		return new JsonModel("minecraft:block/template_wall_side",
			map().put("wall", wall)
		);
	}
	
	public static JsonModel m_wall_side_tall(String wall) {
		return new JsonModel("minecraft:block/template_wall_side_tall",
			map().put("wall", wall)
		);
	}
	
	public static JsonModel m_bottom_side_top(String top, String side, String bottom) {
		return new JsonModel("minecraft:block/cube_bottom_top",
			map()
				.put("top", top)
				.put("bottom", bottom)
				.put("side", side)
				.put("particle", "#top")
		);
	}
	
	public static JsonModel m_column(String side, String end) {
		return new JsonModel("minecraft:block/cube_column",
			map()
				.put("side", side)
				.put("end", end)
				.put("particle", "#end")
		);
	}
	
	public static JsonModel m_particle(String particlePath) {
		return new JsonModel("",
			map()
				.put("particle", particlePath)
		);
	}
}
