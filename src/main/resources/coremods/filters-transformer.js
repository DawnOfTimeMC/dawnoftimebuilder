function initializeCoreMod() {
    return {
        'set_creative_tab': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.client.gui.screen.inventory.CreativeScreen'
            },
            'transformer': function(classNode) {
                log("Patching CreativeScreen...");

                patch({
                    obfName: "func_147050_b",
                    name: "selectTab",
                    desc: "(Lnet/minecraft/item/ItemGroup;)V",
                    patch: patch_CreativeScreen_setCurrentCreativeTab
                }, classNode);

                return classNode;
            }
        },
        'potion_offset': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.client.gui.DisplayEffectsScreen'
            },
            'transformer': function(classNode) {
                log("Patching DisplayEffectsScreen...");

                patch({
                    obfName: "func_238811_b_",
                    name: "renderEffects",
                    desc: "(Lcom/mojang/blaze3d/matrix/MatrixStack;)V",
                    patch: patch_DisplayEffectsScreen_drawActivePotionEffects
                }, classNode);

                patch({
                    obfName: "func_175378_g",
                    name: "checkEffectRendering",
                    desc: "()V",
                    patch: patch_DisplayEffectsScreen_updateActivePotionEffects
                }, classNode);

                return classNode;
            }
        }
    }
}

function findMethod(methods, entry) {
    var length = methods.length;
    for(var i = 0; i < length; i++) {
        var method = methods[i];
        if((method.name.equals(entry.obfName) || method.name.equals(entry.name)) && method.desc.equals(entry.desc)) {
            return method;
        }
    }
    return null;
}

function patch(entry, classNode) {
    var method = findMethod(classNode.methods, entry);
    var name = classNode.name.replace("/", ".") + "#" + entry.name + entry.desc;
    if(method !== null) {
        log("Starting to patch: " + name);
        if(entry.patch(method)) {
            log("Successfully patched: " + name);
        } else {
            log("Failed to patch: " + name);
        }
    } else {
        log("Failed to find method: " + name);
    }
}

var Opcodes = Java.type('org.objectweb.asm.Opcodes');
var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
var LabelNode = Java.type('org.objectweb.asm.tree.LabelNode');
var FieldInsnNode = Java.type('org.objectweb.asm.tree.FieldInsnNode');
var TypeInsnNode = Java.type('org.objectweb.asm.tree.TypeInsnNode');
var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
var FrameNode = Java.type('org.objectweb.asm.tree.FrameNode');

function findFirstMethodInsnNode(method, instruction, code) {
    var foundNode = null;
    var instructions = method.instructions.toArray();
    var length = instructions.length;
    for (var i = 0; i < length; i++) {
        var node = instructions[i];
        if(node.getOpcode() == code) {
            if(instruction.matches(node.name) && instruction.desc.equals(node.desc)) {
                return node;
            }
        }
    }
    return null;
}

function findFirstFieldInsnNode(method, instruction) {
     var foundNode = null;
     var instructions = method.instructions.toArray();
     var length = instructions.length;
     for (var i = 0; i < length; i++) {
         var node = instructions[i];
         if(node.getOpcode() == Opcodes.GETFIELD) {
             if(instruction.matches(node.name) && instruction.desc.equals(node.desc)) {
                 return node;
             }
         }
     }
     return null;
}

function findFirstIntInsnNode(method, instruction, code) {
     var foundNode = null;
     var instructions = method.instructions.toArray();
     var length = instructions.length;
     for (var i = 0; i < length; i++) {
         var node = instructions[i];
         if(node.getOpcode() == code && node.operand == instruction.operand) {
             return node;
         }
     }
     return null;
}

function patch_CreativeScreen_setCurrentCreativeTab(method) {
    var findInstruction = {
        obfName: "func_148329_a",
        name: "scrollTo",
        desc: "(F)V",
        matches: function(s) {
            return s.equals(this.obfName) || s.equals(this.name);
        }
    };
    var node = findFirstMethodInsnNode(method, findInstruction, Opcodes.INVOKEVIRTUAL);
    if(node !== null)
    {
        method.instructions.insert(node, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "org/dawnoftimebuilder/client/gui/filters/CreativeInventoryFilters", "onCreativeTabChange", "(Lnet/minecraft/client/gui/screen/inventory/CreativeScreen;Lnet/minecraft/item/ItemGroup;)V", false));
        method.instructions.insert(node, new VarInsnNode(Opcodes.ALOAD, 1));
        method.instructions.insert(node, new VarInsnNode(Opcodes.ALOAD, 0));
        method.instructions.insert(node, new FieldInsnNode(Opcodes.GETFIELD, "org/dawnoftimebuilder/DawnOfTimeBuilder", "events", "Lorg/dawnoftimebuilder/client/gui/filters/CreativeInventoryFilters;"));
        method.instructions.insert(node, new MethodInsnNode(Opcodes.INVOKESTATIC, "org/dawnoftimebuilder/DawnOfTimeBuilder", "get", "()Lorg/dawnoftimebuilder/DawnOfTimeBuilder;", false))
        return true;
    }
    return false;
}

function patch_DisplayEffectsScreen_drawActivePotionEffects(method) {
    var findInstruction = {
        obfName: "field_147003_i",
        name: "leftPos",
        desc: "I",
        matches: function(s) {
            return s.equals(this.obfName) || s.equals(this.name);
        }
    };
    var node = findFirstFieldInsnNode(method, findInstruction);
    if(node !== null)
    {
        var nextNode = node.getNext();
        method.instructions.remove(nextNode);
        method.instructions.insert(node, new MethodInsnNode(Opcodes.INVOKESTATIC, "org/dawnoftimebuilder/util/filters/Hooks", "getPotionEffectOffset", "(Lnet/minecraft/client/gui/DisplayEffectsScreen;)I", false));
        method.instructions.insert(node, new VarInsnNode(Opcodes.ALOAD, 0));
        return true;
    }
    return false;
}

function patch_DisplayEffectsScreen_updateActivePotionEffects(method) {
    var instruction = {
        operand: 160
    };
    var node = findFirstIntInsnNode(method, instruction, Opcodes.SIPUSH);
    if(node !== null)
    {
        method.instructions.insert(node, new MethodInsnNode(Opcodes.INVOKESTATIC, "org/dawnoftimebuilder/util/filters/Hooks", "getEffectsGuiOffset", "(Lnet/minecraft/client/gui/DisplayEffectsScreen;)I", false));
        method.instructions.insert(node, new VarInsnNode(Opcodes.ALOAD, 0));
        method.instructions.remove(node);
        return true;
    }
    return false;
}

function log(s) {
    print("[filters-transformer.js] " + s);
}
