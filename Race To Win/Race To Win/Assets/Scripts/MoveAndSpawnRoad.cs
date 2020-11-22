using UnityEngine;
using UnityEditor;

public class MoveAndSpawnRoad : MonoBehaviour {

    public float speed = 1.5f;
    public GameObject road;

    void Update() {
        transform.Translate(Vector3.down * speed * Time.deltaTime);

        if (transform.position.y < -8f) {
            Instantiate(road, new Vector3(0.026f, 9.38f, 0), Quaternion.identity);
            Destroy(gameObject);
        }
    }
}
