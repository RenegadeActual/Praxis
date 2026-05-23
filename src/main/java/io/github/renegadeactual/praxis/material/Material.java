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

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A substance in the Praxis Material system - a metal, material, alloy, or compound.
 *
 * Materials are independent of any specific physical form. (Material, MaterialForm) pairs registered as an actual item.
 */
public final class Material {
    private final String id;
    private final String displayName;
    private final String formula;
    private final List<String> elementsContained;
    private final int color;
    private final String requiredHarvestTier;
    private final Set<MaterialForm> availableForms;

    // Mineralogy data (not currently shown to player)
    private final float hardnessMohs;
    private final float density;
    private final String geologicalContext;

    private Material(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "Material id must be set");
        this.displayName = Objects.requireNonNull(builder.displayName, "Material display name must be set");
        this.formula = Objects.requireNonNullElse(builder.formula, "");
        this.elementsContained = List.copyOf(builder.elementsContained);
        this.color = builder.color;
        this.requiredHarvestTier = Objects.requireNonNullElse(builder.requiredHarvestTier, "stone");
        this.availableForms = EnumSet.copyOf(builder.availableForms);
        this.hardnessMohs = builder.hardnessMohs;
        this.density = builder.density;
        this.geologicalContext = Objects.requireNonNullElse(builder.geologicalContext, "");
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String formula() { return formula; }
    public List<String> elementsContained() { return elementsContained; }
    public int color() { return color; }
    public String requiredHarvestTier() { return requiredHarvestTier; }
    public Set<MaterialForm> availableForms() { return availableForms; }
    public float hardnessMohs() { return hardnessMohs; }
    public float density() { return density; }
    public String geologicalContext() { return geologicalContext; }

    public boolean hasForm(MaterialForm form) {
        return availableForms.contains(form);
    }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public static final class Builder {
        private final String id;
        private String displayName;
        private String formula;
        private List<String> elementsContained = List.of();
        private int color = 0xFFFFFF;
        private String requiredHarvestTier;
        private final Set<MaterialForm> availableForms = EnumSet.noneOf(MaterialForm.class);
        private float hardnessMohs = 0.0f;
        private float density = 0.0f;
        private String geologicalContext;

        private Builder(String id) {
            this.id = id;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder formula(String formula) {
            this.formula = formula;
            return this;
        }

        public Builder elementsContained(String... elements) {
            this.elementsContained = List.of(elements);
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder requiredHarvestTier(String tier) {
            this.requiredHarvestTier = tier;
            return this;
        }

        public Builder forms(MaterialForm... forms) {
            for (MaterialForm form : forms) {
                this.availableForms.add(form);
            }
            return this;
        }

        public Builder hardnessMohs(float hardnessMohs) {
            this.hardnessMohs = hardnessMohs;
            return this;
        }

        public Builder density(float density) {
            this.density = density;
            return this;
        }

        public Builder geologicalContext(String geologicalContext) {
            this.geologicalContext = geologicalContext;
            return this;
        }

        public Material build() {
            if (displayName == null) {
                throw new IllegalStateException("Material '" + id + "' must have a displayName");
            }
            if (availableForms.isEmpty()) {
                throw new IllegalStateException("Material '" + id + "' must have at least one form");
            }
            return new Material(this);
        }
    }
}
