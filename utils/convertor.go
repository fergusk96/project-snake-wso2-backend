package utils

import (
	"github.com/jinzhu/copier"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

func IsValidObjectID(id string) bool {
	_, err := primitive.ObjectIDFromHex(id)
	return err == nil
}

func MapTo[T any, V any](from *V) (*T, error) {
	var to T
	err := copier.Copy(&to, from)
	if err != nil {
		return nil, err
	}
	return &to, nil
}
