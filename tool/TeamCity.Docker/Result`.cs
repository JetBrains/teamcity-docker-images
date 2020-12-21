namespace TeamCity.Docker
{
    using IoC;

    internal readonly struct Result<T>
    {
        [CanBeNull] public readonly T Value;
        public readonly Result State;

        public Result([CanBeNull] T value, Result state = Result.Success)
        {
            Value = value;
            State = state;
        }
    }
}
