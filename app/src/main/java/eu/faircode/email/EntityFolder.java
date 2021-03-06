package eu.faircode.email;

/*
    This file is part of Safe email.

    Safe email is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    NetGuard is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with NetGuard.  If not, see <http://www.gnu.org/licenses/>.

    Copyright 2018 by Marcel Bokhorst (M66B)
*/

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        tableName = EntityFolder.TABLE_NAME,
        foreignKeys = {
                @ForeignKey(childColumns = "account", entity = EntityAccount.class, parentColumns = "id", onDelete = CASCADE)
        },
        indices = {
                @Index(value = {"account", "name"}, unique = true),
                @Index(value = {"account"}),
                @Index(value = {"name"}),
                @Index(value = {"type"})
        }
)
public class EntityFolder {
    static final String TABLE_NAME = "folder";

    static final String TYPE_INBOX = "Inbox";
    static final String TYPE_OUTBOX = "Outbox";
    static final String TYPE_ARCHIVE = "All";
    static final String TYPE_DRAFTS = "Drafts";
    static final String TYPE_TRASH = "Trash";
    static final String TYPE_JUNK = "Junk";
    static final String TYPE_SENT = "Sent";
    static final String TYPE_USER = "User";

    static final List<String> STANDARD_FOLDER_ATTR = Arrays.asList(
            "All",
            "Drafts",
            "Trash",
            "Junk",
            "Sent"
    );
    static final List<String> STANDARD_FOLDER_TYPE = Arrays.asList(
            TYPE_ARCHIVE,
            TYPE_DRAFTS,
            TYPE_TRASH,
            TYPE_JUNK,
            TYPE_SENT
    ); // Must match STANDARD_FOLDER_ATTR

    static boolean isOutgoing(String type) {
        return (TYPE_OUTBOX.equals(type) || TYPE_DRAFTS.equals(type) || TYPE_SENT.equals(type));
    }

    @PrimaryKey(autoGenerate = true)
    public Long id;
    public Long account; // Outbox = null
    @NonNull
    public String name;
    @NonNull
    public String type;
    @NonNull
    public Boolean synchronize;
    @NonNull
    public Integer after; // days

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EntityFolder) {
            EntityFolder other = (EntityFolder) obj;
            return (this.account == null ? other.account == null : this.account.equals(other.account) &&
                    this.name.equals(other.name) &&
                    this.type.equals(other.type) &&
                    this.synchronize.equals(other.synchronize) &&
                    this.after.equals(other.after));
        } else
            return false;
    }
}
