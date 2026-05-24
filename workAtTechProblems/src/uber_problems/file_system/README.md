# Design an In-Memory File System

Design and implement an in-memory hierarchical file system supporting:

- `mkdir/create`
- `delete`
- `move`
- `pwd`
- `cd <path>`

The system supports:

- `Folder`
- `File`

Hierarchy:

```text
/
 └── Folder
      └── File / Folder
```

The system starts at the root directory `/`.

---

# Requirements

## Paths
- Paths may be:
    - **Absolute** → start with `/`
    - **Relative** → no leading `/`
- `/` is the path separator.
- Multiple consecutive `/` should be treated as a single `/`.

---

# Special Segments

- `.` → current directory
- `..` → parent directory
    - Root’s parent is itself.

---

# APIs

## `String pwd()`

Return the absolute path of the current directory.

Examples:

```text
/           -> root
/a/b/c      -> nested path
```

Rules:
- No trailing slash except for root.

---

## `void mkdir(String path)`

Create folders along the given path.

Behavior:
- Works like `mkdir -p`
- Missing parent folders are auto-created.
- Creating an existing folder is a no-op.

Constraints:
- `*` is not allowed in `mkdir`.

---

## `void delete(String path)`

Delete a file or folder.

Behavior:
- Deleting a folder removes its entire subtree.
- Deleting a non-existing path should fail gracefully.

---

## `void move(String source, String destination)`

Move a file/folder from `source` to `destination`.

Requirements:
- Preserve subtree structure while moving folders.
- Moving a directory into its own subtree is invalid.
- Paths and parent-child relationships must remain consistent after move.

Expected discussion:
- efficient lookup
- metadata updates
- subtree path updates
- tradeoffs between:
    - storing full paths
    - storing parent pointers + children maps

> Interview focus is primarily on the design and implementation of the `move` operation.

---

## `void cd(String path)`

Change the current directory.

Behavior:
- Supports absolute and relative paths.
- Normalize `.` and `..` during traversal.
- If traversal fails at any segment, current directory remains unchanged.

---

# Wildcard `*` in `cd`

`*` is allowed only in `cd`.

It matches exactly one segment using the deterministic rule below:

1. Prefer a child directory
2. If multiple children exist, choose the lexicographically smallest
3. If no children exist, use `.`
4. Otherwise use `..`

This guarantees a single valid resolution path.

---

# Examples

```text
CWD = /

mkdir /a/b/c

cd a/b
pwd() -> /a/b

cd *
pwd() -> /a/b/c

cd ../*
pwd() -> /a/b/c
```

If `/` has children `{a, x}`:

```text
cd /*
```

chooses `/a`.

If `/` has no children:

```text
cd *
```

remains at `/`.

---

# Failure Cases

```text
cd /nope/*/x
```

If `/nope` does not exist:
- command fails
- current directory remains unchanged

---

# Follow-Up Discussion

One possible approach is:

```text
Map<Path, Entity>
```

where move operations update paths recursively.

Discuss:
- time complexity of move
- subtree updates
- memory tradeoffs
- alternative designs using tree structures:
    - parent pointers
    - children maps
    - inode-style references