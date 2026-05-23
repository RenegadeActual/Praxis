/**
 * MIT License
 *
 * Copyright (c) 2026 William Whatley
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.renegadeactual.praxis.material;

/**
 * The different physical forms a Material can exist in.
 *
 * Not every Material has every form. Each Material declares which forms it supports.
 */
public enum MaterialForm {

    /* The mineable block place in the world by worldgen. */
    ORE_BLOCK,

    /* The item dropped when an ore block is mined */
    RAW_ORE,

    /* Intermediate product from hand-crushing or low-tier machining */
    CRUSHED_ORE,

    /* Fully reduced powder ready for smelting or further chemistry */
    DUST,

    /* The smelted metal ingot. Standard 1/9th of metal block */
    INGOT,

    /* 1/9th of metal ingot. Used in finer crafting and as a smelting output for some chains */
    NUGGET,

    /* A pressed plate of metal. Used for mid-game crafting recipies */
    PLATE,

    /* Compressed storage form, 9 ingots = 1 block */
    BLOCK;
}
