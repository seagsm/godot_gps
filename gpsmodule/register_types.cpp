#include <version_generated.gen.h>

#if VERSION_MAJOR == 3
#include <core/class_db.h>
#include <core/engine.h>
#else
#include "object_type_db.h"
#include "core/globals.h"
#endif

#include "register_types.h"

void register_mygps_types() {
#if VERSION_MAJOR == 3
    Engine::get_singleton()->add_singleton(Engine::Singleton("GpsModule", memnew(GodotGpsModule)));
#else
    Globals::get_singleton()->add_singleton(Globals::Singleton("GpsModule", memnew(GodotGpsModule)));
#endif
}

void unregister_gpsmodule_types() {
}
