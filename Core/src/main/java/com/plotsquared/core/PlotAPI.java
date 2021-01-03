/*
 *       _____  _       _    _____                                _
 *      |  __ \| |     | |  / ____|                              | |
 *      | |__) | | ___ | |_| (___   __ _ _   _  __ _ _ __ ___  __| |
 *      |  ___/| |/ _ \| __|\___ \ / _` | | | |/ _` | '__/ _ \/ _` |
 *      | |    | | (_) | |_ ____) | (_| | |_| | (_| | | |  __/ (_| |
 *      |_|    |_|\___/ \__|_____/ \__, |\__,_|\__,_|_|  \___|\__,_|
 *                                    | |
 *                                    |_|
 *            PlotSquared plot management system for Minecraft
 *                  Copyright (C) 2021 IntellectualSites
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.plotsquared.core;

import com.plotsquared.core.configuration.caption.Caption;
import com.plotsquared.core.configuration.caption.StaticCaption;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.player.ConsolePlayer;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import com.plotsquared.core.queue.GlobalBlockQueue;
import com.plotsquared.core.util.ChunkManager;
import com.plotsquared.core.util.EventDispatcher;
import com.plotsquared.core.util.SchematicHandler;
import com.plotsquared.core.util.query.PlotQuery;
import net.kyori.adventure.text.minimessage.Template;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * PlotSquared API.
 *
 * <p>Useful classes:
 * <ul>
 * <li>{@link PlotPlayer}</li>
 * <li>{@link Plot}</li>
 * <li>{@link Location}</li>
 * <li>{@link PlotArea}</li>
 * <li>{@link PlotSquared}</li>
 * </ul>
 *
 * @version 5
 */
@SuppressWarnings({"unused", "WeakerAccess"}) public class PlotAPI {

    public PlotAPI() {
    }

    /**
     * Gets all plots.
     *
     * @return all plots
     */
    public Set<Plot> getAllPlots() {
        return PlotQuery.newQuery().allPlots().asSet();
    }

    /**
     * Gets all plots for a player.
     *
     * @param player Player, whose plots to search for
     * @return all plots that a player owns
     */
    public Set<Plot> getPlayerPlots(PlotPlayer<?> player) {
        return PlotQuery.newQuery().ownedBy(player).asSet();
    }

    /**
     * Adds a plot world.
     *
     * @param plotArea Plot World Object
     * @see PlotSquared#addPlotArea(PlotArea)
     */
    public void addPlotArea(PlotArea plotArea) {
        PlotSquared.get().addPlotArea(plotArea);
    }

    /**
     * ChunkManager class contains several useful methods.
     * <ul>
     * <li>Chunk deletion</li>
     * <li>Moving or copying regions</li>
     * <li>Plot swapping</li>
     * <li>Entity Tracking</li>
     * <li>Region Regeneration</li>
     * </ul>
     *
     * @return ChunkManager
     * @see ChunkManager
     */
    public ChunkManager getChunkManager() {
        return PlotSquared.platform().injector().getInstance(ChunkManager.class);
    }

    /**
     * Gets the block/biome set queue
     *
     * @return GlobalBlockQueue.IMP
     */
    public GlobalBlockQueue getBlockQueue() {
        return PlotSquared.platform().globalBlockQueue();
    }

    /**
     * SchematicHandler class contains methods related to pasting, reading
     * and writing schematics.
     *
     * @return SchematicHandler
     * @see SchematicHandler
     */
    public SchematicHandler getSchematicHandler() {
        return PlotSquared.platform().injector().getInstance(SchematicHandler.class);
    }

    /**
     * Gets a list of PlotAreas in the world.
     *
     * @param world The world to check for plot areas
     * @return A set of PlotAreas
     */
    public Set<PlotArea> getPlotAreas(String world) {
        if (world == null) {
            return Collections.emptySet();
        }
        return PlotSquared.get().getPlotAreaManager().getPlotAreasSet(world);
    }

    /**
     * Send a message to the console. The message supports color codes.
     *
     * @param message      the message
     * @param replacements Variable replacements
     */
    public void sendConsoleMessage(@Nonnull final String message, final Template... replacements) {
        ConsolePlayer.getConsole().sendMessage(StaticCaption.of(message), replacements);
        // TODO: Re-implement
        // PlotSquared.log(message);
    }

    /**
     * Sends a message to the console.
     *
     * @param caption      the message
     * @param replacements Variable replacements
     */
    public void sendConsoleMessage(@Nonnull final Caption caption, final Template... replacements) {
        ConsolePlayer.getConsole().sendMessage(caption, replacements);
    }

    /**
     * Gets the PlotSquared class.
     *
     * @return PlotSquared Class
     * @see PlotSquared
     */
    public PlotSquared getPlotSquared() {
        return PlotSquared.get();
    }

    /**
     * Gets the PlotPlayer for a UUID.
     *
     * <p><i>Please note that PlotSquared can be configured to provide
     * different UUIDs than bukkit</i>
     *
     * @param uuid the uuid of the player to wrap
     * @return a {@code PlotPlayer}
     */
    @Nullable public PlotPlayer<?> wrapPlayer(@Nonnull final UUID uuid) {
        return PlotSquared.platform().playerManager().getPlayerIfExists(uuid);
    }

    /**
     * Gets the PlotPlayer for a username.
     *
     * @param player the player to wrap
     * @return a {@code PlotPlayer}
     */
    @Nullable public PlotPlayer<?> wrapPlayer(@Nonnull final String player) {
        return PlotSquared.platform().playerManager().getPlayerIfExists(player);
    }

    /**
     * Registers a listener for PlotSquared Events
     *
     * @param listener the listener class to register
     * @see EventDispatcher#registerListener(Object)
     */
    public void registerListener(Object listener) {
        PlotSquared.get().getEventDispatcher().registerListener(listener);
    }
}