/*
 * This file is part of AckCord, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2017 Katrix
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
package net.katsstuff.ackcord.data

trait GetGuild {
  def guildId: GuildId

  /**
    * The guild for this object
    */
  def guild(implicit snapshot: CacheSnapshot): Option[Guild] = snapshot.getGuild(guildId)
}

trait GetGuildOpt {
  def guildId: Option[GuildId]

  /**
    * The guild for this object
    */
  def guild(implicit snapshot: CacheSnapshot): Option[Guild] = guildId.flatMap(snapshot.getGuild)
}

trait GetUser {
  def userId: UserId

  /**
    * The user for this object
    */
  def user(implicit snapshot: CacheSnapshot): Option[User] = snapshot.getUser(userId)
}

trait GetChannel {
  def channelId: ChannelId

  /**
    * Resolve the channelId of this object as a channel
    */
  def channel(implicit snapshot: CacheSnapshot): Option[Channel] = snapshot.getChannel(channelId)

  /**
    * Resolve the channelId of this object as a dm channel
    */
  def dmChannel(implicit snapshot: CacheSnapshot): Option[DMChannel] = snapshot.getDmChannel(channelId)

  /**
    * Resolve the channelId of this object as a text channel
    */
  def tChannel(implicit snapshot: CacheSnapshot): Option[TChannel] = channel.collect {
    case tChannel: TChannel => tChannel
  }
}

trait GetGuildChannel extends GetChannel {
  def guildId: GuildId

  /**
    * Resolve the channelId of this object as a guild channel
    */
  def guildChannel(implicit snapshot: CacheSnapshot): Option[GuildChannel] =
    snapshot.getGuildChannel(guildId, channelId)

  /**
    * Resolve the channelId of this object as a guild text channel
    */
  def tGuildChannel(implicit snapshot: CacheSnapshot): Option[TGuildChannel] = guildChannel.collect {
    case tChannel: TGuildChannel => tChannel
  }

  /**
    * Resolve the channelId of this object as a guild voice channel
    */
  def vGuildChannel(implicit snapshot: CacheSnapshot): Option[VGuildChannel] = guildChannel.collect {
    case vChannel: VGuildChannel => vChannel
  }

  /**
    * Get the guild through getting the guild channel.
    */
  def guild(implicit snapshot: CacheSnapshot): Option[Guild] = guildChannel.flatMap(_.guild)
}

trait GetChannelOpt {
  def channelId: Option[ChannelId]

  /**
    * Resolve the channelId of this object as a channel.
    */
  def channel(implicit snapshot: CacheSnapshot):   Option[Channel]   = channelId.flatMap(snapshot.getChannel)

  /**
    * Resolve the channelId of this object as a dm channel.
    */
  def dmChannel(implicit snapshot: CacheSnapshot): Option[DMChannel] = channelId.flatMap(snapshot.getDmChannel)

  /**
    * Resolve the channelId of this object as a text channel
    */
  def tChannel(implicit snapshot: CacheSnapshot): Option[TChannel] = channel.collect {
    case tChannel: TChannel => tChannel
  }
}
