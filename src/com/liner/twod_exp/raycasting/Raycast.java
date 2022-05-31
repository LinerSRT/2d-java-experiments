package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.math.Vector2;

public class Raycast implements Constants {
    public Vector2 rayOrigin;
    public Vector2 displacement;
    public double angle;
    public final double maxDistance;
    public final int[][] map;

    public Raycast(double x, double y, double angle, int[][] map, double maxDistance) {
        this.rayOrigin = new Vector2(x, y);
        this.angle = angle;
        this.map = map;
        this.maxDistance = maxDistance;
        this.displacement = new Vector2(x, y);
    }

    public Raycast(Vector2 rayOrigin, double angle, int[][] map, double maxDistance) {
        this.rayOrigin = rayOrigin;
        this.angle = angle;
        this.map = map;
        this.maxDistance = maxDistance;
        this.displacement = new Vector2(rayOrigin);
    }

    public RaycastResult raycast() {
        RaycastResult raycastResult = null;
        Vector2 rayPosition = new Vector2(rayOrigin);
        Vector2 rayDirection = new Vector2();
        Vector2 tileIntersected = new Vector2();
        boolean intersected = false;
        double intersectionDistance = 0;
        while (!intersected && intersectionDistance < maxDistance) {
            try {
                if (angle > Math.PI / 2 && angle <= 3 * Math.PI / 2) {
                    if (rayPosition.x % cellSize == 0) {
                        rayDirection.x = ((int) (rayPosition.x / cellSize - 1) * cellSize - rayPosition.x) / Math.cos(angle);
                    } else {
                        rayDirection.x = ((int) (rayPosition.x / cellSize) * cellSize - rayPosition.x) / Math.cos(angle);
                    }
                } else if (angle <= Math.PI / 2 || angle > 3 * Math.PI / 2) {
                    rayDirection.x = ((int) (rayPosition.x / cellSize + 1) * cellSize - rayPosition.x) / Math.cos(angle);
                }
            } catch (ArithmeticException e) {
                rayDirection.x = Double.MAX_VALUE;
            }
            try {
                if (angle >= 0 && angle < Math.PI) {
                    rayDirection.y = ((int) (rayPosition.y / cellSize + 1) * cellSize - rayPosition.y) / Math.sin(angle);
                }
                if (angle >= Math.PI && angle < 2 * Math.PI) {
                    if (rayPosition.y % cellSize == 0) {
                        rayDirection.y = ((int) (rayPosition.y / cellSize - 1) * cellSize - rayPosition.y) / Math.sin(angle);
                    } else {
                        rayDirection.y = ((int) (rayPosition.y / cellSize) * cellSize - rayPosition.y) / Math.sin(angle);
                    }
                }
            } catch (ArithmeticException e) {
                rayDirection.y = Double.MAX_VALUE;
            }
            if (rayDirection.y < rayDirection.x) {
                rayPosition.x = stepX(rayPosition.x, rayDirection.y);
                rayPosition.y = stepY(rayPosition.y, rayDirection.y);
            } else if (rayDirection.x < rayDirection.y) {
                rayPosition.x = stepX(rayPosition.x, rayDirection.x);
                rayPosition.y = stepY(rayPosition.y, rayDirection.x);
            } else {
                rayPosition.x = stepX(rayPosition.x, rayDirection.x);
                rayPosition.y = stepY(rayPosition.y, rayDirection.y);
            }
            intersectionDistance = Math.sqrt((displacement.x - rayOrigin.x) * (displacement.x - rayOrigin.x) + (displacement.y - rayOrigin.y) * (displacement.y - rayOrigin.y));
            if (intersectionDistance >= cellSize * cellSize && (Math.abs(Math.cos(angle)) <= sigma || Math.abs(Math.sin(angle)) <= sigma)) {
                intersected = true;
            }
            if (rayDirection.x < rayDirection.y) {
                if (angle > Math.PI / 2 && angle <= 3 * Math.PI / 2) {
                    if (map[indexStepY(rayPosition.y, 0)][indexStepX(rayPosition.x, -1)] > 0) {
                        intersected = true;
                        raycastResult = new RaycastResult(
                                intersectionDistance,
                                rayOrigin,
                                rayPosition,
                                indexStepX(rayPosition.x, -1), indexStepY(rayPosition.y, 0),
                                RaycastResult.Direction.WEST
                        );
                    }
                }
                if (angle <= Math.PI / 2 || angle > 3 * Math.PI / 2) {
                    if (map[indexStepY(rayPosition.y, 0)][indexStepX(rayPosition.x, 0)] > 0) {
                        intersected = true;
                        raycastResult = new RaycastResult(
                                intersectionDistance,
                                rayOrigin,
                                rayPosition,
                                indexStepX(rayPosition.x, 0), indexStepY(rayPosition.y, 0),
                                RaycastResult.Direction.EAST
                        );
                    }
                }
            } else if (rayDirection.y <= rayDirection.x) {
                if (angle >= 0 && angle < Math.PI) {
                    if (map[indexStepY(rayPosition.y, 0)][indexStepX(rayPosition.x, 0)] > 0) {
                        intersected = true;
                        raycastResult = new RaycastResult(
                                intersectionDistance,
                                rayOrigin,
                                rayPosition,
                                indexStepX(rayPosition.x, 0), indexStepY(rayPosition.y, 0),
                                RaycastResult.Direction.NORTH
                        );
                    }
                }
                if (angle >= Math.PI && angle < 2 * Math.PI) {
                    if (map[indexStepY(rayPosition.y, -1)][indexStepX(rayPosition.x, 0)] > 0) {
                        intersected = true;
                        raycastResult = new RaycastResult(
                                intersectionDistance,
                                rayOrigin,
                                rayPosition,
                                indexStepX(rayPosition.x, 0),
                                indexStepY(rayPosition.y, -1),
                                RaycastResult.Direction.SOUTH
                        );
                    }
                }
            }
        }
        return raycastResult;
    }


    public double stepX(double cx, double d) {
        displacement.x += d * Math.cos(angle);
        if (cx + d * Math.cos(angle) >= map[0].length * cellSize) {
            return (cx + d * Math.cos(angle)) % (map[0].length * cellSize);
        } else if (cx + d * Math.cos(angle) < 0) {
            return cellSize * map[0].length + cx + d * Math.cos(angle);
        }
        return cx + d * Math.cos(angle);
    }

    public double stepY(double cy, double d) {
        displacement.y += d * Math.sin(angle);
        if (cy + d * Math.sin(angle) >= map.length * cellSize) {
            return (cy + d * Math.sin(angle)) % (map.length * cellSize);
        } else if (cy + d * Math.sin(angle) < 0) {
            return cellSize * map.length + cy + d * Math.sin(angle);
        }
        return cy + d * Math.sin(angle);
    }

    public int indexStepX(double cx, int step) {
        int index = (int) (cx / cellSize) + step;
        if (index < 0) {
            return map[0].length + index;
        } else if (index >= map[0].length) {
            return index % map[0].length;
        }
        return index;
    }

    public int indexStepY(double cy, int step) {
        int index = (int) (cy / cellSize) + step;
        if (index < 0) {
            return map.length + index;
        } else if (index >= map.length) {
            return index % map.length;
        }
        return index;
    }


}